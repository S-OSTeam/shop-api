package sosteam.deamhome.domain.log.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import sosteam.deamhome.domain.log.entity.ReviewLikeLog

@Repository
interface ReviewLikeLogRepository : CoroutineCrudRepository<ReviewLikeLog, Long>