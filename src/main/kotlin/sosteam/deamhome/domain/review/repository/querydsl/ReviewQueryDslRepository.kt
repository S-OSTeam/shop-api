package sosteam.deamhome.domain.review.repository.querydsl

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import sosteam.deamhome.domain.review.entity.Review

interface ReviewQueryDslRepository : QuerydslR2dbcRepository<Review, Long>
