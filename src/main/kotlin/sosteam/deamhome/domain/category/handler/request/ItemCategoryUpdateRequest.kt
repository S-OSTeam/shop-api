package sosteam.deamhome.domain.category.handler.request

import java.util.UUID

data class ItemCategoryUpdateRequest (
    val publicId: String,
    val parentPublicId: String?,
    val title: String?
) {

}