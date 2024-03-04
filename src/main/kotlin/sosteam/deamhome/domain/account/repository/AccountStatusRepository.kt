package sosteam.deamhome.domain.account.repository


import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.domain.account.repository.custom.AccountStatusRepositoryCustom
import sosteam.deamhome.global.attribute.Status

@GraphQlRepository
interface AccountStatusRepository : CoroutineCrudRepository<AccountStatus, Long>, AccountStatusRepositoryCustom {
	suspend fun deleteByUserId(userId: String)
	suspend fun findByUserId(userId: String): AccountStatus?
	suspend fun findAllByStatus(status: Status): Flow<AccountStatus>
}
