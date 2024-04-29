package sosteam.deamhome.domain.account.service

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.repository.AccountRepository

@Service
@Transactional
class AccountDeleteService(private val accountRepository: AccountRepository) {
	suspend fun deleteAccount(account: Account) {
		accountRepository.delete(account)
	}
}