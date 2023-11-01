package sosteam.deamhome.domain.category.repository.custom

import kotlinx.coroutines.flow.Flow
import sosteam.deamhome.domain.category.dto.ItemCategoryDTO2


interface ItemCategoryRepositoryCustom {

    fun getItemsContainsTitle(title: String) : Flow<ItemCategoryDTO2>
}