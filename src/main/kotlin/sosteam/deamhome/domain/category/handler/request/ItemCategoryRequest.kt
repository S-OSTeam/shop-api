package sosteam.deamhome.domain.category.handler.request

import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.global.entity.DTO

data class ItemCategoryRequest (

    val title: String,
    val parentPublicId: String?

): DTO {

    override fun asDomain(): ItemCategory {
        return ItemCategory(
            id = null,
            title = this.title
        ).apply {
            parentPublicId = this@ItemCategoryRequest.parentPublicId?: publicId
        }

    }


}