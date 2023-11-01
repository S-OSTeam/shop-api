package sosteam.deamhome.domain.category.dto

import sosteam.deamhome.domain.category.entity.ItemDetailCategory

class ItemCategoryDTO2 (
    val title: String? = null,
    val itemDetailCategories: MutableList<ItemDetailCategory> = mutableListOf()
) {

}