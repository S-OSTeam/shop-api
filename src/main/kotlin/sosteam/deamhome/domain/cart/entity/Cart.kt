package sosteam.deamhome.domain.cart.entity

import lombok.Builder
import lombok.Setter
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
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
	@DBRef
	@Setter
	private val item: Item = item
	
	@DBRef(lazy = true)
	@Setter
	private val account: Account = account
}