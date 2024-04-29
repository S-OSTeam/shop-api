package sosteam.deamhome.domain.coupon.handler.response

import sosteam.deamhome.domain.coupon.entity.Coupon
import sosteam.deamhome.domain.coupon.entity.CouponDiscountType
import sosteam.deamhome.domain.coupon.entity.CouponType
import java.time.OffsetDateTime

class CouponResponse(
	val publicId: String,
	val title: String,
	val content: String,
	val couponType: CouponType,
	val couponDiscountType: CouponDiscountType,
	val userId: String?,
	val itemIds: List<String?>,
	val categoryIds: List<String?>,
	val status: Boolean,
	val startDate: OffsetDateTime?,
	val endDate: OffsetDateTime?,
	val discount: Int,
	val minPurchaseAmount: Int?
) {
	companion object {
		fun fromCoupon(coupon: Coupon): CouponResponse {
			return CouponResponse(
				publicId = coupon.publicId,
				title = coupon.title,
				content = coupon.content,
				couponType = coupon.couponType,
				couponDiscountType = coupon.couponDiscountType,
				userId = coupon.userId,
				itemIds = coupon.itemIds,
				categoryIds = coupon.categoryIds,
				status = coupon.status,
				startDate = coupon.startDate,
				endDate = coupon.endDate,
				discount = coupon.discount,
				minPurchaseAmount = coupon.minPurchaseAmount
			)
		}
	}
}