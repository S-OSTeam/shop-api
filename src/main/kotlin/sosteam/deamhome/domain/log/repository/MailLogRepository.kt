package sosteam.deamhome.domain.log.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import sosteam.deamhome.domain.log.entity.MailLog

interface MailLogRepository: CoroutineCrudRepository<MailLog, Long> {
}