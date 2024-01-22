package sosteam.deamhome.domain.log.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import sosteam.deamhome.domain.log.entity.ReviewLikeLog

interface ReviewLikeLogRepository: CoroutineCrudRepository<ReviewLikeLog, Long> {
}