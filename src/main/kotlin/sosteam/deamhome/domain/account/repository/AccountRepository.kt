package sosteam.deamhome.domain.account.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.account.entity.Account

@Repository
interface AccountRepository: ReactiveMongoRepository<Account, String> {
}