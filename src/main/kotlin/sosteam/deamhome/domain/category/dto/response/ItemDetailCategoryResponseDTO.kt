package sosteam.deamhome.domain.category.dto.response

import sosteam.deamhome.domain.category.entity.ItemDetailCategory

class ItemDetailCategoryResponseDTO(
    val title: String? = null,
    val itemIdList: MutableList<String> = mutableListOf()
) {
    companion object {
        fun fromItemDetailCategory(itemDetailCategory: ItemDetailCategory): ItemDetailCategoryResponseDTO {
            return ItemDetailCategoryResponseDTO(
                title = itemDetailCategory.title,
                itemIdList = itemDetailCategory.itemIdList
            )
        }
    }
}