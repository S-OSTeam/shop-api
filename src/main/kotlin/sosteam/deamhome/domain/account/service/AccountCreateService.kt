package sosteam.deamhome.domain.account.service

import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.account.handler.response.AccountResponse
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.account.repository.AccountStatusRepository
import sosteam.deamhome.domain.auth.handler.request.AccountCreateRequest

@Service
@Transactional
@RequiredArgsConstructor
class AccountCreateService(
	private val accountRepository: AccountRepository,
	private val accountStatusRepository: AccountStatusRepository,
	private val accountStatusValidService: AccountStatusValidService,
	private val passwordEncoder: PasswordEncoder
) {
	suspend fun createAccount(accountCreateRequest: AccountCreateRequest): AccountResponse {
		
		val snsId = accountStatusValidService.getSnsId(
			accountCreateRequest.userId,
			accountCreateRequest.sns,
			accountCreateRequest.snsCode
		)
		val accountStatus = accountCreateRequest.asStatus()
		accountStatus.snsId = snsId
		
		val account = accountCreateRequest.asDomain()
		account.pwd = passwordEncoder.encode(account.pwd)
		
		val result = accountRepository.save(account)
		accountStatus.accountId = result.id
		accountStatusRepository.save(accountStatus)
		
		return AccountResponse.fromAccount(account)
	}
}