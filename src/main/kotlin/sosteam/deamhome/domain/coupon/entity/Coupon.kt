package sosteam.deamhome.domain.coupon.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.domain.item.entity.CouponType
import sosteam.deamhome.global.entity.LogEntity

@Table
class Coupon(
	@Id
	var id: Long?,
	var publicId: String,
	var title: String,
	var content: String,
	var couponType: CouponType,
	var userId: String?,
	var itemId: String?,
	var status: Boolean,
	var discount: Int
) : LogEntity()