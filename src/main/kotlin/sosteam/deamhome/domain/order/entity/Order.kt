package sosteam.deamhome.domain.order.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.attribute.OrderStatus
import sosteam.deamhome.global.entity.LogEntity
import java.time.LocalDateTime

@Table("order-shop")
class Order(
	@Id
	var id: Long?,
	var publicId: String,
	val address: String,
	val mainDoorPassWord: String,
	val deliveryRequest: String,
	val userName: String,
	var userNickname: String,
	var addressee: String,
	val email: String,
	val phone: String,
	var memo: String,
	val userId: String,
	var totalPrice: Int,
	val content: String,
	var payType: String,
	val appNo: String,
	val bankId: String,
	val orderDateTime: LocalDateTime,
	val paymentDateTime: LocalDateTime?,
	val releaseDateTime: LocalDateTime?,
	val refundDateTime: LocalDateTime?,
	val deliveryDateTime: LocalDateTime?,
	val approveDatetime: LocalDateTime?,
	val couponIds: List<String?>,
	val pointPrice: Int,
	var shippingCompany: String,
	val downloadDays: LocalDateTime,
	val reasonRefund: String
) : LogEntity() {
	
	var orderStatus: OrderStatus = OrderStatus.PENDING
	
	var adminMemo: String? = null
	
	var isPaid = false
	var isRefund = false
	
	private val items: MutableList<String> = mutableListOf()
	private val optionItems: MutableList<String> = mutableListOf()
	
	@Transient
	val itemsCount: Int = 0
	
	fun addItem(itemId: String): List<String> {
		items.add(itemId)
		return items
	}
	
	fun addOptionItem(itemId: String): List<String> {
		items.add(itemId)
		return items
	}
}