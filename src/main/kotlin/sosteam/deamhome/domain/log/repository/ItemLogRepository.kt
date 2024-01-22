package sosteam.deamhome.domain.log.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import sosteam.deamhome.domain.log.entity.ItemLog

interface ItemLogRepository: CoroutineCrudRepository<ItemLog, Long> {
}