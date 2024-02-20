package sosteam.deamhome.domain.coupon.repository.impl

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
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
			predicate.or(compareCouponTypeAsString("USER_SPECIFIC").and(coupon.userId.eq(it)))
		}
		if (!itemIds.filterNotNull().isEmpty()) {
			itemIds.filterNotNull().forEach { itemId ->
				predicate.or(
					compareCouponTypeAsString("BUNDLE_DISCOUNT")
						.or(compareCouponTypeAsString("PRODUCT_SPECIFIC"))
						.and(Expressions.stringTemplate("array_to_string({0}, ',')", coupon.itemIds).contains(itemId))
				)
			}
		}
		predicate.or(
			compareCouponTypeAsString("MIN_PURCHASE_AMOUNT")
				.and(coupon.minPurchaseAmount.loe(orderPrice))
		)
		if (!categoryIds.filterNotNull().isEmpty()) {
			categoryIds.filterNotNull().forEach { categoryId ->
				predicate.or(
					compareCouponTypeAsString("CATEGORY_SPECIFIC")
						.and(
							Expressions.stringTemplate("array_to_string({0}, ',')", coupon.categoryIds)
								.contains(categoryId)
						)
				)
			}
		}
		links.filterNotNull().forEach { link ->
			predicate.or(
				compareCouponTypeAsString("SPECIFIC_LINK")
					.and(Expressions.stringTemplate("array_to_string({0}, ',')", coupon.links).contains(link))
			)
		}
		
		return predicate
	}
	
	fun compareCouponTypeAsString(couponTypeValue: String): BooleanExpression {
		return Expressions.booleanTemplate("{0} = {1}", coupon.couponType, couponTypeValue)
	}
}