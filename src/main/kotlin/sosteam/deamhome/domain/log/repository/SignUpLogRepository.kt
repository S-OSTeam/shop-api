package sosteam.deamhome.domain.log.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import sosteam.deamhome.domain.log.entity.SignUpLog

interface SignUpLogRepository: ReactiveMongoRepository<SignUpLog,String> {
}