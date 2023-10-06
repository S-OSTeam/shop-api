package sosteam.deamhome.domain.account.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import sosteam.deamhome.domain.account.entity.Account

interface AccountRepository : ReactiveMongoRepository<Account, String>