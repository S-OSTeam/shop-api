package sosteam.deamhome.domain.order.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.attribute.OrderStatus

// order 로 해도 괜찮으려나?
@Table("order")
class Order(
	// id 가 왜 없었지? 내가 맘대로 만들어도 되나?
	@Id
	var id: Long?,
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