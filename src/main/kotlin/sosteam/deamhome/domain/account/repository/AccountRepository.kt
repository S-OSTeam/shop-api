package sosteam.deamhome.domain.account.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.repository.custom.AccountRepositoryCustom

@GraphQlRepository
interface AccountRepository : ReactiveMongoRepository<Account, String>, AccountRepositoryCustom {
	suspend fun findAccountByUserId(userId: String): Account?
	suspend fun deleteAccountById(id: String)
}


