package sosteam.deamhome.domain.category.handler.response

import sosteam.deamhome.domain.category.entity.ItemCategory

class ItemCategoryTreeResponse (
    val publicId: String,
    val title: String,
    val children: MutableList<ItemCategoryTreeResponse> = mutableListOf()
) {
    companion object {
        fun fromItemCategory(itemCategory: ItemCategory): ItemCategoryTreeResponse {
            return ItemCategoryTreeResponse(
                publicId = itemCategory.publicId,
                title = itemCategory.title,
                children = mutableListOf()
            )
        }
    }
}