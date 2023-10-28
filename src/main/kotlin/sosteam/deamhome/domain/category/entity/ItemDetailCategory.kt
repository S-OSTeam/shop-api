package sosteam.deamhome.domain.category.entity

import lombok.Builder
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.global.entity.BaseEntity

@Document
@Builder
data class ItemDetailCategory(
	@Indexed(unique = true)
	var title: String
) : BaseEntity() {

	var items: MutableList<Item> = mutableListOf()
	
	fun modifyItems(items: MutableList<Item>): MutableList<Item> {
		this.items = items
		return this.items
	}
}