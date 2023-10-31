package sosteam.deamhome.domain.account.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.account.entity.Account

interface AccountRepository : ReactiveMongoRepository<Account, String> {
    override fun findAll(): Flux<Account>
    suspend fun findByUserId(userId:String): Account?
    override fun deleteById(id: String):Mono<Void>
}
