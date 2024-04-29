package sosteam.deamhome.domain.coupon.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.entity.BaseEntity
import java.time.OffsetDateTime

@Table("coupon")
class Coupon(
	@Id
	var id: Long?,
	@Column("public_id")
	var publicId: String,
	var title: String,
	var content: String,
	@Column("coupon_type")
	var couponType: CouponType,
	@Column("coupon_discount_type")
	var couponDiscountType: CouponDiscountType,
	@Column("user_id")
	var userId: String?,
	@Column("item_ids")
	var itemIds: List<String?>,
	@Column("category_ids")
	var categoryIds: List<String?>,
	var status: Boolean,
	@Column("start_date")
	var startDate: OffsetDateTime?,
	@Column("end_date")
	var endDate: OffsetDateTime?,
	var discount: Int,
	@Column("min_purchase_amount")
	var minPurchaseAmount: Int? = null,
	var links: List<String?>
) : BaseEntity()