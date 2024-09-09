package sosteam.deamhome.domain.item.handler.request

data class ItemCategoryUpdateRequest(
	val publicId: String,
	val parentPublicId: String?,
	val title: String?
) {

}