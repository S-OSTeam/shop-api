package sosteam.deamhome.domain.account.repository


import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import sosteam.deamhome.domain.account.entity.AccountStatus

interface AccountStatusRepository : ReactiveMongoRepository<AccountStatus, String> {
	suspend fun deleteByUserId(userId: String)
	suspend fun findByUserId(userId: String): AccountStatus?
	override fun findAll(): Flux<AccountStatus>
}
