package sosteam.deamhome.domain.coupon.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.coupon.entity.Coupon
import sosteam.deamhome.domain.coupon.repository.custom.CouponRepositoryCustom

@GraphQlRepository
interface CouponRepository : CoroutineCrudRepository<Coupon, Long>, CouponRepositoryCustom {
	suspend fun findByPublicId(publicId: String): Coupon?
	
	suspend fun deleteByPublicId(publicId: String): Long
}