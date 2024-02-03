package sosteam.deamhome.domain.coupon.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.entity.LogEntity
import java.time.LocalDateTime

@Table
class Coupon(
	@Id
	var id: Long?,
	var publicId: String,
	var couponNumber: String?,
	var title: String,
	var content: String,
	var couponType: CouponType,
	var couponDiscountType: CouponDiscountType,
	var userId: String?,
	var itemIds: List<String?>,
	var categoryIds: List<String?>,
	var status: Boolean,
	var startDate: LocalDateTime,
	var endDate: LocalDateTime,
	var discount: Int,
	var minPurchaseAmount: Int? = null,
	var linkHistory: Boolean = false
) : LogEntity()