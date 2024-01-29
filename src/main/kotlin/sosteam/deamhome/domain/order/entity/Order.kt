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
	val userName: String,
	var userNickname: String,
	val email: String,
	val phone: String,
	var memo: String,
	val userId: String,
	var totalPrice: Int,
	val content: String,
	var payType: String,
	val appNo: String,
	val bankInfo: String,
	val paymentDateTime: LocalDateTime,
	val refundDateTime: LocalDateTime,
	val approveDatetime: LocalDateTime,
	val couponName: String,
	val couponPrice: Int,
	val pointPrice: Int,
	val downloadDays: LocalDateTime
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