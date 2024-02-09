package sosteam.deamhome.domain.category.handler.response

import sosteam.deamhome.domain.category.entity.ItemCategory
import java.util.*

class ItemCategoryResponse (
    val title: String,
    val publicId: UUID,
    val parentPublicId: UUID
) {
    companion object {
        fun fromItemCategory(itemCategory: ItemCategory): ItemCategoryResponse {
            return ItemCategoryResponse(
                title = itemCategory.title,
                publicId = itemCategory.publicId!!,
                parentPublicId = itemCategory.parentPublicId!!
            )
        }
    }
}