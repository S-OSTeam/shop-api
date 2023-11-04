package sosteam.deamhome.domain.category.dto.response

import sosteam.deamhome.domain.category.entity.ItemDetailCategory

class ItemDetailCategoryResponse(
    val title: String? = null,
    val itemIdList: MutableList<String> = mutableListOf()
) {
}