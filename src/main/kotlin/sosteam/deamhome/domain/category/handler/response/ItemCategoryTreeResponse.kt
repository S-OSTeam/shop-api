package sosteam.deamhome.domain.category.handler.response

import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.global.category.handler.response.CategoryTreeResponse

class ItemCategoryTreeResponse (
    override val publicId: String,
    override val title: String,
    override val children: MutableList<CategoryTreeResponse<ItemCategory>> = mutableListOf()
): CategoryTreeResponse<ItemCategory>() {
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