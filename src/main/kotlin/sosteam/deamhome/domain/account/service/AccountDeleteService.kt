package sosteam.deamhome.domain.account.service

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.repository.AccountRepository

@Service
class AccountDeleteService(private val accountRepository: AccountRepository) {
	suspend fun deleteAccount(account: Account) {
		accountRepository.delete(account).awaitSingle()
	}
}