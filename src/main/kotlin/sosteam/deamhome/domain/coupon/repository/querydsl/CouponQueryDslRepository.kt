package sosteam.deamhome.domain.coupon.repository.querydsl

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import sosteam.deamhome.domain.coupon.entity.Coupon

interface CouponQueryDslRepository : QuerydslR2dbcRepository<Coupon, Long>