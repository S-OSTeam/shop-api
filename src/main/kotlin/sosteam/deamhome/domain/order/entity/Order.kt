package sosteam.deamhome.domain.order.entity

import com.github.f4b6a3.ulid.UlidCreator
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.attribute.OrderStatus
import sosteam.deamhome.global.entity.BaseEntity
import java.time.OffsetDateTime

@Table("order_shop")
class Order(
	@Id
	var id: Long?,
	val address: String,
	val mainDoorPassWord: String,
	val deliveryRequest: String?,
	val userName: String,
	var userNickname: String,
	// 수신인
	var addressee: String,
	val email: String,
	val phone: String,
	var memo: String?,
	val userId: String,
	var totalPrice: Int,
	val content: String?,
	var payType: String,
	val appNo: String,
	val bankId: String,
	val orderDateTime: OffsetDateTime,
	val paymentDateTime: OffsetDateTime?,
	val releaseDateTime: OffsetDateTime?,
	val refundDateTime: OffsetDateTime?,
	val deliveryDateTime: OffsetDateTime?,
	val approveDatetime: OffsetDateTime?,
	val couponIds: List<String?>?,
	val pointPrice: Int,
	var shippingCompany: String?,
	val downloadDays: OffsetDateTime,
	val reasonRefund: String?,
) : BaseEntity() {
	val publicId: String = UlidCreator.getMonotonicUlid().toString().replace("-", "")
	var orderStatus: OrderStatus = OrderStatus.PENDING
	var adminMemo: String? = null
	var isPaid = false
	var isRefund = false
}