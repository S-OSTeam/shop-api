package sosteam.deamhome.domain.category.handler.request

import java.util.UUID

data class ItemCategoryUpdateRequest (
    val publicId: UUID,
    val parentPublicId: UUID?,
    val title: String?
) {

}