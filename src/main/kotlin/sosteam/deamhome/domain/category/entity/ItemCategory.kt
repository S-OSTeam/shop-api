package sosteam.deamhome.domain.category.entity

import lombok.Builder
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.global.entity.BaseEntity

@Document
@Builder
data class ItemCategory(
	var title: String,
	@DocumentReference(lazy = true) var itemDetailCategory: ItemDetailCategory?
) : BaseEntity() {
	
	@DocumentReference(lazy = true)
	var items: List<Item> = ArrayList()
	
	fun modifyItems(items: List<Item>): List<Item> {
		this.items = items
		return this.items
	}
}