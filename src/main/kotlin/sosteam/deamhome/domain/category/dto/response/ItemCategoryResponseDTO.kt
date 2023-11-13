package sosteam.deamhome.domain.category.dto.response

import sosteam.deamhome.domain.account.dto.response.AccountResponseDTO
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.entity.ItemDetailCategory

class ItemCategoryResponseDTO (
    val title: String? = null,
    val itemDetailCategories: MutableList<ItemDetailCategory> = mutableListOf()
) {
    companion object {
        fun fromItemCategory(itemCategory: ItemCategory): ItemCategoryResponseDTO {
            return ItemCategoryResponseDTO(
                title = itemCategory.title,
                itemDetailCategories = itemCategory.itemDetailCategories
            )
        }
    }
}