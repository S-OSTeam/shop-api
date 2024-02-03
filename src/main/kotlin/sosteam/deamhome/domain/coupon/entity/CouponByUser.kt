package sosteam.deamhome.domain.coupon.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
class CouponByUser(
	@Id
	var id: Long?,
	var publicId: String,
)