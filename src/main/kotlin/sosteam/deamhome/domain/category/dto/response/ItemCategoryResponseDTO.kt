package sosteam.deamhome.domain.category.dto.response

import sosteam.deamhome.domain.category.entity.ItemCategory

class ItemCategoryResponseDTO (
    val title: String? = null,
    val itemDetailCategories: MutableList<ItemDetailCategoryResponseDTO> = mutableListOf()
) {
    companion object {
        fun fromItemCategory(itemCategory: ItemCategory): ItemCategoryResponseDTO {
            return ItemCategoryResponseDTO(
                title = itemCategory.title,
                itemDetailCategories = itemCategory.itemDetailCategories.map {
                    ItemDetailCategoryResponseDTO.fromItemDetailCategory(it)
                }.toMutableList()
            )
        }
    }
}