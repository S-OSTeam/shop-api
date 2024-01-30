package sosteam.deamhome.domain.coupon.repository.impl

import com.querydsl.core.BooleanBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.coupon.entity.Coupon
import sosteam.deamhome.domain.coupon.entity.QCoupon
import sosteam.deamhome.domain.coupon.exception.CouponIllegalArgumentIdException
import sosteam.deamhome.domain.coupon.repository.custom.CouponRepositoryCustom
import sosteam.deamhome.domain.coupon.repository.querydsl.CouponQueryDslRepository

@GraphQlRepository
class CouponRepositoryImpl(
	private val couponQueryDslRepository: CouponQueryDslRepository
) : CouponRepositoryCustom {
	private val coupon = QCoupon.coupon
	
	override fun findCoupons(userId: String?, itemId: String?): Flow<Coupon> {
		val predicate = booleanBuilder(userId, itemId)
		val finalPredicate = predicate.value ?: throw CouponIllegalArgumentIdException()
		return couponQueryDslRepository.findAll(finalPredicate).asFlow()
	}
	
	private fun booleanBuilder(userId: String?, itemId: String?): BooleanBuilder {
		val predicate = BooleanBuilder()
		
		userId?.let {
			predicate.and(coupon.userId.eq(it))
		}
		itemId?.let {
			predicate.and(coupon.itemId.eq(it))
		}
		return predicate
	}
}