package sosteam.deamhome.domain.account.service

import kotlinx.coroutines.reactor.awaitSingle
import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.handler.response.AccountResponse
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.account.repository.AccountStatusRepository
import sosteam.deamhome.domain.auth.handler.request.AccountCreateRequest

@Service
@RequiredArgsConstructor
class AccountCreateService(
	private val accountRepository: AccountRepository,
	private val accountStatusRepository: AccountStatusRepository,
	private val passwordEncoder: PasswordEncoder
) {
	suspend fun createAccount(accountCreateRequest: AccountCreateRequest): AccountResponse {
		val accountStatus = accountCreateRequest.asStatus()
		val account = accountCreateRequest.asDomain()
		accountStatus.accountId = account.id
		account.pwd = passwordEncoder.encode(account.pwd)
		
		accountStatusRepository.save(accountStatus).awaitSingle()
		val result = accountRepository.save(account).awaitSingle()
		
		return AccountResponse.fromAccount(result)
	}
}