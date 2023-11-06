package sosteam.deamhome.domain.account.repository


import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.domain.account.repository.custom.AccountStatusRepositoryCustom

@GraphQlRepository
interface AccountStatusRepository : ReactiveMongoRepository<AccountStatus, String>, AccountStatusRepositoryCustom {
	suspend fun deleteByUserId(userId: String)
	suspend fun findByUserId(userId: String): AccountStatus?
}
