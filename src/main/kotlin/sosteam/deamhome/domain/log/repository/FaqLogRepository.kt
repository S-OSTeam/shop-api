package sosteam.deamhome.domain.log.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import sosteam.deamhome.domain.log.entity.FaqLog

interface FaqLogRepository: ReactiveMongoRepository<FaqLog,String> {
}