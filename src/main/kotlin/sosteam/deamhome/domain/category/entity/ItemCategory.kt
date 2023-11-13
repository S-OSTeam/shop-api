package sosteam.deamhome.domain.category.entity

import lombok.Builder
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import sosteam.deamhome.global.entity.BaseEntity


@Document
@Builder
data class ItemCategory(
    @Indexed(unique = true)
	var title: String
) : BaseEntity(){

    var itemDetailCategories: MutableList<ItemDetailCategory> = mutableListOf()

    fun modifyDetailCategory(itemDetailCategories: MutableList<ItemDetailCategory>): List<ItemDetailCategory> {
        this.itemDetailCategories = itemDetailCategories
        return this.itemDetailCategories
    }
    
}