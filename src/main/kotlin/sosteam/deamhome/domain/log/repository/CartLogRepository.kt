package sosteam.deamhome.domain.log.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import sosteam.deamhome.domain.log.entity.CartLog

interface CartLogRepository: ReactiveMongoRepository<CartLog,String> {
}