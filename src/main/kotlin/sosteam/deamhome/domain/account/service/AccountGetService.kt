package sosteam.deamhome.domain.account.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.repository.AccountRepository

@Service
class AccountGetService(
	private val accountRepository: AccountRepository,
) {
	fun getAllAccounts(): Flow<Account> {
		return accountRepository.findAll().asFlow()
	}
	
	suspend fun findByUserId(userId: String): Account {
		return accountRepository.findAccountByUserId(userId)
	}
}