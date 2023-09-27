package sosteam.deamhome.domain.category.entity

import lombok.Builder
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.global.entity.BaseEntity

@Document
@Builder
class ItemCategory(
	var title: String,
	itemDetailCategory: ItemDetailCategory
) : BaseEntity() {
	
	@DBRef(lazy = true)
	var items: List<Item> = ArrayList()
	
	@DBRef(lazy = true)
	val itemDetailCategory: ItemDetailCategory = itemDetailCategory
	
	fun modifyItems(items: List<Item>): List<Item> {
		this.items = items
		return this.items
	}
}