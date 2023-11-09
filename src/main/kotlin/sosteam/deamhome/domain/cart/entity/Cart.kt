package sosteam.deamhome.domain.cart.entity

import lombok.Builder
import lombok.Setter
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.global.entity.LogEntity

@Document
@Builder
class Cart(
	@Setter
	var fullPrice: Int,
	var discountPrice: Int,
	var realPrice: Int,
	var cnt: Int,
	item: Item,
	account: Account,
) : LogEntity() {
	@DocumentReference
	@Setter
	private val item: Item = item
	
	@DocumentReference(lazy = true)
	@Setter
	private val account: Account = account
}