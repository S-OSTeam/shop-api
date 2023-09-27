package sosteam.deamhome.domain.item.entity

import lombok.Builder
import lombok.Setter
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.global.entity.LogEntity

@Document
@Builder
class Wishlist(
	account: Account,
	items: List<Item>
) : LogEntity() {
	@DBRef(lazy = true)
	@Setter
	val account: Account = account
	
	@DBRef(lazy = true)
	val items: ArrayList<Item> = items as ArrayList<Item>
	
	fun addItem(item: Item): List<Item> {
		items.add(item)
		return items
	}
}