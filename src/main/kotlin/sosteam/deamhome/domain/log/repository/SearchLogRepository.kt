package sosteam.deamhome.domain.log.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import sosteam.deamhome.domain.log.entity.SearchLog

interface SearchLogRepository: CoroutineCrudRepository<SearchLog, Long> {
}