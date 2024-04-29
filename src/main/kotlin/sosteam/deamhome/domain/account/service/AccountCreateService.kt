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
	private val passwordEncoder: PasswordEncoder
) {
	suspend fun createAccount(accountCreateRequest: AccountCreateRequest): AccountResponse {
		
		val accountStatus = accountCreateRequest.asStatus()
		val account = accountCreateRequest.asDomain()
		account.pwd = passwordEncoder.encode(account.pwd)
		
		val result = accountRepository.save(account)
		accountStatus.accountId = result.id
		accountStatusRepository.save(accountStatus)
		
		return AccountResponse.fromAccount(account)
	}
}