package sosteam.deamhome.domain.order.entity

import com.github.f4b6a3.ulid.UlidCreator
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.attribute.OrderStatus
import sosteam.deamhome.global.entity.BaseEntity
import java.time.OffsetDateTime

@Table("order_shop")
class Order(
	@Id
	var id: Long?,
	val address: String,
	@Column("main_door_password")
	val mainDoorPassword: String,
	@Column("delivery_request")
	val deliveryRequest: String?,
	@Column("user_name")
	val userName: String,
	@Column("user_nickname")
	var userNickname: String,
	// 수신인
	var addressee: String,
	val email: String,
	val phone: String,
	var memo: String?,
	@Column("user_id")
	val userId: String,
	@Column("total_price")
	var totalPrice: Int,
	val content: String?,
	@Column("pay_type")
	var payType: String,
	@Column("app_no")
	val appNo: String,
	@Column("bank_id")
	val bankId: String,
	@Column("order_date_time")
	val orderDateTime: OffsetDateTime,
	@Column("payment_date_time")
	val paymentDateTime: OffsetDateTime?,
	@Column("release_date_time")
	val releaseDateTime: OffsetDateTime?,
	@Column("refund_date_time")
	val refundDateTime: OffsetDateTime?,
	@Column("delivery_date_time")
	val deliveryDateTime: OffsetDateTime?,
	@Column("approve_date_time")
	val approveDatetime: OffsetDateTime?,
	@Column("coupon_ids")
	val couponIds: List<String?>?,
	@Column("point_price")
	val pointPrice: Int,
	@Column("shipping_company")
	var shippingCompany: String?,
	@Column("download_days")
	val downloadDays: OffsetDateTime,
	@Column("reason_refund")
	val reasonRefund: String?,
) : BaseEntity() {
	@Column("public_id")
	val publicId: String = UlidCreator.getMonotonicUlid().toString().replace("-", "")
	@Column("order_status")
	var orderStatus: OrderStatus = OrderStatus.PENDING
	@Column("admin_memo")
	var adminMemo: String? = null
	@Column("is_paid")
	var isPaid = false
	@Column("is_refund")
	var isRefund = false
}