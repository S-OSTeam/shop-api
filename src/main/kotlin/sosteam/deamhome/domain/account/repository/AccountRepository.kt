package sosteam.deamhome.domain.account.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.graphql.data.GraphQlRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.repository.custom.AccountCustomRepository

@GraphQlRepository
interface AccountRepository : ReactiveMongoRepository<Account, String>, AccountCustomRepository {
	override fun findAll(): Flux<Account>
	suspend fun findByUserId(userId: String): Account?
	override fun deleteById(id: String): Mono<Void>
}


