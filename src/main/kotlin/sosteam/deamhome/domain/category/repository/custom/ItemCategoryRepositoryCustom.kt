package sosteam.deamhome.domain.category.repository.custom

import kotlinx.coroutines.flow.Flow
import sosteam.deamhome.domain.category.entity.ItemCategory

interface ItemCategoryRepositoryCustom {

    fun findItemCategoriesContainTitle(title: String) : Flow<ItemCategory>

    fun findAllItemCategories(): Flow<ItemCategory>

}