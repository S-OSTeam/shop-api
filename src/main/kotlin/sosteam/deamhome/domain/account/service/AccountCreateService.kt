package sosteam.deamhome.domain.account.service

import kotlinx.coroutines.reactor.awaitSingle
import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.dto.request.AccountRequestDTO
import sosteam.deamhome.domain.account.dto.response.AccountResponseDTO
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.account.repository.AccountStatusRepository

@Service
@RequiredArgsConstructor
class AccountCreateService(
	private val accountRepository: AccountRepository,
	private val accountStatusRepository: AccountStatusRepository,
	private val passwordEncoder: PasswordEncoder
) {
	suspend fun createAccount(accountRequestDTO: AccountRequestDTO): AccountResponseDTO {
		
		val accountStatus = accountRequestDTO.asStatus()
		val account = accountRequestDTO.asDomain()
		account.pwd = passwordEncoder.encode(account.pwd)
		
		accountStatusRepository.save(accountStatus).awaitSingle()
		val result = accountRepository.save(account).awaitSingle()
		
		return AccountResponseDTO.fromAccount(result)
	}
}