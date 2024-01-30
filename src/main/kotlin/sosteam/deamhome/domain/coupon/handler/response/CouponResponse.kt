package sosteam.deamhome.domain.coupon.handler.response

import sosteam.deamhome.domain.coupon.entity.Coupon
import sosteam.deamhome.domain.coupon.entity.CouponType
import java.time.LocalDateTime

class CouponResponse(
	val publicId: String,
	val title: String,
	val content: String,
	val couponType: CouponType,
	val userId: String?,
	val itemId: String?,
	val status: Boolean,
	val startDate: LocalDateTime,
	val endDate: LocalDateTime,
	val discount: Int
) {
	companion object {
		fun fromCoupon(coupon: Coupon): CouponResponse {
			return CouponResponse(
				publicId = coupon.publicId,
				title = coupon.title,
				content = coupon.content,
				couponType = coupon.couponType,
				userId = coupon.userId,
				itemId = coupon.itemId,
				status = coupon.status,
				startDate = coupon.startDate,
				endDate = coupon.endDate,
				discount = coupon.discount
			)
		}
	}
}