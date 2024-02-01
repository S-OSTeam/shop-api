package sosteam.deamhome.domain.log.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import sosteam.deamhome.domain.log.entity.CouponLog

interface CouponLogRepository : CoroutineCrudRepository<CouponLog, Long>