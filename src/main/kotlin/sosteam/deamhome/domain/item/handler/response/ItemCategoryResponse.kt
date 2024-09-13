package sosteam.deamhome.domain.item.handler.response

import sosteam.deamhome.domain.item.entity.ItemCategory

class ItemCategoryResponse(
	val title: String,
	val publicId: String,
	val parentPublicId: String
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