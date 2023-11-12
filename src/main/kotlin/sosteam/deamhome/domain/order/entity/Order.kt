package sosteam.deamhome.domain.order.entity

import lombok.Builder
import org.springframework.data.mongodb.core.mapping.Document
import sosteam.deamhome.global.attribute.OrderStatus

@Document
@Builder
class Order(
	val address: String,
	val userName: String,
	val email: String,
	val phone: String,
	val content: String,
	var accountId: String,
) {
	
	var orderStatus: OrderStatus = OrderStatus.PENDING
	
	var adminMemo: String? = null
	
	var isPaid = false
	var isRefund = false
	
	private val items: ArrayList<String> = ArrayList()
	
	fun addItem(itemId: String): List<String> {
		items.add(itemId)
		return items
	}
}