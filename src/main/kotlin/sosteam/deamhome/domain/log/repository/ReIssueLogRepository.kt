package sosteam.deamhome.domain.log.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import sosteam.deamhome.domain.log.entity.ReIssueLog

interface ReIssueLogRepository: CoroutineCrudRepository<ReIssueLog, Long> {
}