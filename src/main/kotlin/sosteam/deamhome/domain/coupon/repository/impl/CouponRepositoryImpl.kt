package sosteam.deamhome.domain.coupon.repository.impl

import com.querydsl.core.BooleanBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.coupon.entity.Coupon
import sosteam.deamhome.domain.coupon.entity.CouponType
import sosteam.deamhome.domain.coupon.entity.QCoupon
import sosteam.deamhome.domain.coupon.exception.CouponIllegalArgumentIdException
import sosteam.deamhome.domain.coupon.repository.custom.CouponRepositoryCustom
import sosteam.deamhome.domain.coupon.repository.querydsl.CouponQueryDslRepository

@GraphQlRepository
class CouponRepositoryImpl(
	private val couponQueryDslRepository: CouponQueryDslRepository
) : CouponRepositoryCustom {
	private val coupon = QCoupon.coupon
	
	override fun findCoupons(
		userId: String?,
		itemIds: List<String?>,
		categoryIds: List<String?>,
		links: List<String?>,
		orderPrice: Int
	): Flow<Coupon> {
		val predicate = booleanBuilder(userId, itemIds, categoryIds, links, orderPrice)
		val finalPredicate = predicate.value ?: throw CouponIllegalArgumentIdException()
		return couponQueryDslRepository.findAll(finalPredicate).asFlow()
	}
	
	private fun booleanBuilder(
		userId: String?,
		itemIds: List<String?>,
		categoryIds: List<String?>,
		links: List<String?>,
		orderPrice: Int
	): BooleanBuilder {
		val predicate = BooleanBuilder()
		
		userId?.let {
			predicate.or(coupon.couponType.eq(CouponType.USER_SPECIFIC).and(coupon.userId.eq(it)))
		}
		itemIds.let { ids ->
			ids.forEach { itemId ->
				predicate.or(
					coupon.couponType.eq(CouponType.BUNDLE_DISCOUNT)
						.or(coupon.couponType.eq(CouponType.PRODUCT_SPECIFIC))
						.and(coupon.itemIds.contains(itemId))
				)
				predicate.or(
					coupon.couponType.eq(CouponType.MIN_PURCHASE_AMOUNT).and(coupon.minPurchaseAmount.loe(orderPrice))
				)
			}
		}
		categoryIds.let { ids ->
			ids.forEach { categoryId ->
				predicate.or(
					coupon.couponType.eq(CouponType.CATEGORY_SPECIFIC).and(coupon.categoryIds.contains(categoryId))
				)
			}
			
		}
		links.let { links ->
			links.forEach {
				predicate.or(coupon.couponType.eq(CouponType.SPECIFIC_LINK).and(coupon.links.contains(it)))
			}
		}
		
		return predicate
	}
}