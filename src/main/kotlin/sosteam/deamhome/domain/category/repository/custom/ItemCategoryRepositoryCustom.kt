package sosteam.deamhome.domain.category.repository.custom

import kotlinx.coroutines.flow.Flow
import sosteam.deamhome.domain.category.dto.ItemCategoryDTO2
import sosteam.deamhome.domain.item.entity.dto.ItemDTO


interface ItemCategoryRepositoryCustom {

    fun getItemCategoriesContainsTitle(title: String) : Flow<ItemCategoryDTO2>

    fun getItemsContainsTitle(title: String) :Flow<ItemDTO>
}