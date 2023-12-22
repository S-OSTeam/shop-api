package sosteam.deamhome.domain.category.repository.custom

import kotlinx.coroutines.flow.Flow
import sosteam.deamhome.domain.category.entity.ItemCategory

interface ItemCategoryRepositoryCustom {

    fun findItemCategoriesContainsTitle(title: String) : Flow<ItemCategory>

    fun findAllItemCategories(): Flow<ItemCategory>

    suspend fun findItemCategoryById(id: String) : ItemCategory?

}