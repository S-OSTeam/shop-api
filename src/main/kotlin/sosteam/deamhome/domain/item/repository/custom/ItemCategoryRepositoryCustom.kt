package sosteam.deamhome.domain.item.repository.custom

import kotlinx.coroutines.flow.Flow
import sosteam.deamhome.domain.item.entity.ItemCategory

interface ItemCategoryRepositoryCustom {
	
	fun findItemCategoriesContainTitle(title: String): Flow<ItemCategory>
	
	fun findAllItemCategoriesByTitle(title: String): Flow<ItemCategory>
	
	suspend fun findEqualsTitle(title: String): ItemCategory?
	
}