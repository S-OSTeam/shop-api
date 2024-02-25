package sosteam.deamhome.domain.category.handler.request

data class ItemCategoryUpdateRequest (
    val publicId: String,
    val parentPublicId: String?,
    val title: String?
) {

}