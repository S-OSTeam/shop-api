package sosteam.deamhome.domain.account.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.repository.custom.AccountRepositoryCustom

@GraphQlRepository
interface AccountRepository : CoroutineCrudRepository<Account, Long>, AccountRepositoryCustom {
	suspend fun findAccountByUserId(userId: String): Account?

	suspend fun findAccountById(id: Long): Account?

	suspend fun deleteAccountById(id: Long)

	suspend fun findAccountByUserName(userName: String): Account?
}


