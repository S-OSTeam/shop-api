package sosteam.deamhome.domain.log.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.domain.coupon.entity.CouponDiscountType
import sosteam.deamhome.domain.coupon.entity.CouponType
import java.time.LocalDateTime

@Table("CouponLog")
class CouponLog(
	@Id
	var id: Long?,
	var title: String,
	var content: String,
	var couponType: CouponType,
	var couponDiscountType: CouponDiscountType,
	var userId: String,
	var itemId: String,
	var categoryId: String,
	var startDate: LocalDateTime,
	var endDate: LocalDateTime,
	var discount: Int,
	var minPurchaseAmount: Int?,
	var usingDateTime: LocalDateTime,
)