package sosteam.deamhome.domain.category.handler.response

import sosteam.deamhome.domain.category.entity.ItemCategory

class ItemCategoryTreeResponse (
    val title: String,
    val children: MutableList<ItemCategoryTreeResponse> = mutableListOf()
) {
    companion object {
        fun fromItemCategory(itemCategory: ItemCategory): ItemCategoryTreeResponse {
            return ItemCategoryTreeResponse(
                title = itemCategory.title,
                children = mutableListOf()
            )
        }
    }
}