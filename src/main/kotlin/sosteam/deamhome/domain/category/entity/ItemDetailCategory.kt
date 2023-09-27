package sosteam.deamhome.domain.category.entity

import lombok.Builder
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.global.entity.BaseEntity

@Document
@Builder
class ItemDetailCategory(
	var title: String,
) : BaseEntity() {
	
	@DBRef(lazy = true)
	var items: List<Item> = ArrayList()
	
	@DBRef(lazy = true)
	var itemCategories: List<ItemCategory> = ArrayList()
	
	fun modifyItems(items: List<Item>): List<Item> {
		this.items = items
		return this.items
	}
	
	fun modifyCategories(categories: List<ItemCategory>): List<ItemCategory> {
		this.itemCategories = categories
		return this.itemCategories
	}
}