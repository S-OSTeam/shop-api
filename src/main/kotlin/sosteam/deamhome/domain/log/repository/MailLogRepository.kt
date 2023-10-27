package sosteam.deamhome.domain.log.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import sosteam.deamhome.domain.log.entity.MailLog

interface MailLogRepository: ReactiveMongoRepository<MailLog,String>{
}