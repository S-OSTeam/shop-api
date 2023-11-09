package sosteam.deamhome.domain.order.entity

import lombok.Builder
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.item.entity.Item

@Document
@Builder
class Order(
	val address: String,
	val userName: String,
	val email: String,
	val phone: String,
	val content: String,
	account: Account
) {
	
	var adminMemo: String? = null
	
	var isPaid = false
	var isRefund = false
	
	@DocumentReference(lazy = true)
	private val account: Account = account
	
	@DocumentReference(lazy = true)
	private val items: ArrayList<Item> = ArrayList()
	
	fun addItem(item: Item): List<Item> {
		items.add(item)
		return items
	}
}