package sosteam.deamhome.domain.account.repository


import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.account.entity.AccountStatus

interface AccountStatusRepository : ReactiveMongoRepository<AccountStatus, String> {
    fun deleteByUserId(userId:String): Flux<AccountStatus>
    suspend fun findByUserId(userId:String): AccountStatus?
    override fun findAll(): Flux<AccountStatus>
}
