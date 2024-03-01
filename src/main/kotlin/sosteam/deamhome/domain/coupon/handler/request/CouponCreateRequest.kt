package sosteam.deamhome.domain.coupon.handler.request

import sosteam.deamhome.domain.coupon.entity.CouponDiscountType
import sosteam.deamhome.domain.coupon.entity.CouponType
import java.time.OffsetDateTime

data class CouponCreateRequest(
	val title: String,
	val content: String,
	val couponType: CouponType,
	val couponDiscountType: CouponDiscountType,
	val userId: String?,
	val itemIds: List<String?> = emptyList(),
	val categoryIds: List<String?> = emptyList(),
	val startDate: OffsetDateTime? = null,
	val endDate: OffsetDateTime? = null,
	val discount: Int,
	val minPurchaseAmount: Int? = null,
	val links: List<String?> = emptyList()
)