package sosteam.deamhome.domain.account.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import sosteam.deamhome.domain.account.entity.Account

interface AccountRepository :ReactiveMongoRepository<Account, Long> {

    override fun findAll(): Flux<Account>
}