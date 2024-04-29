package sosteam.deamhome.domain.log.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.log.entity.MailLog

@GraphQlRepository
interface MailLogRepository: CoroutineCrudRepository<MailLog, Long> {
}