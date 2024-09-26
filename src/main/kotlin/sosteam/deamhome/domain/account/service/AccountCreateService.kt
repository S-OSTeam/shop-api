package sosteam.deamhome.domain.account.service

import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.account.handler.response.AccountResponse
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.account.repository.AccountStatusRepository
import sosteam.deamhome.domain.auth.exception.AccountInvalidExeption
import sosteam.deamhome.domain.auth.handler.request.AccountCreateRequest
import sosteam.deamhome.global.attribute.SNS

@Service
@Transactional
@RequiredArgsConstructor
class AccountCreateService(
	private val accountRepository: AccountRepository,
	private val accountStatusRepository: AccountStatusRepository,
	private val accountStatusValidService: AccountStatusValidService,
	private val passwordEncoder: PasswordEncoder
) {
	suspend fun createAccount(accountCreateRequest: AccountCreateRequest, snsToken: String?): AccountResponse {
		val snsId = accountStatusValidService.getSnsId(
			accountCreateRequest.userId,
			accountCreateRequest.sns,
			snsToken
		)
		
		accountStatusValidService.isNotExistAccount(
			accountCreateRequest.userId,
			accountCreateRequest.sns,
			snsToken,
			accountCreateRequest.email
		)
		
		val accountStatus = accountCreateRequest.asStatus()
		accountStatus.snsId = snsId
		
		val account = accountCreateRequest.asDomain()
		val sns = accountCreateRequest.sns
		if (sns == SNS.NORMAL) {
			val pattern = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,20}$".toRegex()
			if (account.pwd.isNullOrBlank() || !pattern.matches(account.pwd!!)) {
				throw AccountInvalidExeption()
			}
			account.pwd = passwordEncoder.encode(account.pwd)
		}
		
		account.snsId = snsId
		val result = accountRepository.save(account)
		accountStatus.accountId = result.id
		accountStatus.snsId = snsId
		accountStatusRepository.save(accountStatus)
		
		return AccountResponse.fromAccount(account)
	}
}