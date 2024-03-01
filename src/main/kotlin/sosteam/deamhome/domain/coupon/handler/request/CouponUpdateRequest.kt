package sosteam.deamhome.domain.coupon.handler.request

import sosteam.deamhome.domain.coupon.entity.CouponDiscountType
import sosteam.deamhome.domain.coupon.entity.CouponType
import java.time.OffsetDateTime

data class CouponUpdateRequest(
	val publicId: String,
	val title: String,
	val content: String,
	val couponType: CouponType,
	val couponDiscountType: CouponDiscountType,
	val userId: String?,
	val itemIds: List<String?> = emptyList(),
	val categoryIds: List<String?> = emptyList(),
	val status: Boolean,
	val startDate: OffsetDateTime?,
	val endDate: OffsetDateTime?,
	val discount: Int,
	val minPurchaseAmount: Int? = null,
	val links: List<String?> = emptyList()
)