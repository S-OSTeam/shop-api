package sosteam.deamhome.domain.category.handler.response

import sosteam.deamhome.domain.category.entity.ItemCategory

class ItemCategoryResponse (
    val title: String,
    val publicId: Long,
    val parentPublicId: Long?
) {
    companion object {
        fun fromItemCategory(itemCategory: ItemCategory): ItemCategoryResponse {
            return ItemCategoryResponse(
                title = itemCategory.title,
                publicId = itemCategory.publicId,
                parentPublicId = itemCategory.parentPublicId
            )
        }
    }
}