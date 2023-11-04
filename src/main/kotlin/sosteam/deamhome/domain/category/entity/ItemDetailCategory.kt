package sosteam.deamhome.domain.category.entity

import lombok.Builder
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.global.entity.BaseEntity

@Document
@Builder
data class ItemDetailCategory(
	@Indexed(unique = true)
	var title: String
) : BaseEntity() {

	@DocumentReference(lazy = true)
	var items: MutableList<Item> = mutableListOf()
	
	fun modifyItems(items: MutableList<Item>): MutableList<Item> {
		this.items = items
		return this.items
	}
}