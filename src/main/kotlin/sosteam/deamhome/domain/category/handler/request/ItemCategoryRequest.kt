package sosteam.deamhome.domain.category.handler.request

import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.global.entity.DTO
import java.util.UUID

data class ItemCategoryRequest (
    val title: String,
    val parentPublicId: UUID?
): DTO {

    override fun asDomain(): ItemCategory {
        val requestParentPublicId = parentPublicId
        return ItemCategory(
            id = null,
            title = this.title,
        ).apply {
            parentPublicId = requestParentPublicId?: publicId
        }

    }


}