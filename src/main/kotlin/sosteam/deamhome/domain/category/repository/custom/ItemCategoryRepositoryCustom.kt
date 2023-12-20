package sosteam.deamhome.domain.category.repository.custom

import kotlinx.coroutines.flow.Flow
import sosteam.deamhome.domain.category.entity.ItemCategory

interface ItemCategoryRepositoryCustom {

    fun findItemCategoriesContainsTitle(title: String) : Flow<ItemCategory>
//
//    suspend fun getItemDetailCategoryByTitle(title: String): ItemDetailCategory?
//
//    fun getItemIdsByCategoryTitle(title: String): Flow<String>
//
//    fun getItemIdsByItemDetailCategoryTitle(title: String): Flow<String>
//
//    suspend fun findCategoryByItemId(itemId: String): ItemCategory?
//
////    suspend fun deleteDetailCategoryById(id: String)

}