package sosteam.deamhome.domain.category.repository.custom

import kotlinx.coroutines.flow.Flow
import sosteam.deamhome.domain.category.dto.ItemCategoryDTO2
import sosteam.deamhome.domain.category.dto.response.ItemDetailCategoryResponse


interface ItemCategoryRepositoryCustom {

    fun getItemCategoriesContainsTitle(title: String) : Flow<ItemCategoryDTO2>

    suspend fun getItemDetailCategoryByTitle(title: String): ItemDetailCategoryResponse?

}