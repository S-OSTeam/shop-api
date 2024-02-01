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
	
	override fun findCoupons(userId: String?, itemIds: List<String?>): Flow<Coupon> {
		// TODO: CouponType에 따라 더 찾아야 함
		val predicate = booleanBuilder(userId, itemIds)
		val finalPredicate = predicate.value ?: throw CouponIllegalArgumentIdException()
		return couponQueryDslRepository.findAll(finalPredicate).asFlow()
	}
	
	private fun booleanBuilder(userId: String?, itemIds: List<String?>): BooleanBuilder {
		val predicate = BooleanBuilder()
		
		userId?.let {
			predicate.or(coupon.userId.eq(it))
		}
		itemIds.let { ids ->
			ids.forEach { itemId ->
				predicate.or(coupon.itemIds.contains(itemId))
			}
		}
		return predicate
	}
}