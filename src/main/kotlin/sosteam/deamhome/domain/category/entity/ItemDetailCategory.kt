package sosteam.deamhome.domain.category.entity

import lombok.Builder
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import sosteam.deamhome.domain.category.dto.response.ItemDetailCategoryResponseDTO
import sosteam.deamhome.global.entity.BaseEntity

@Document
@Builder
data class ItemDetailCategory(
	@Indexed(unique = true)
	var title: String,
	var itemIdList: MutableList<String> = mutableListOf()
) : BaseEntity() {

	fun modifyItems(itemIdList: MutableList<String>): MutableList<String> {
		this.itemIdList = itemIdList
		return this.itemIdList
	}

}