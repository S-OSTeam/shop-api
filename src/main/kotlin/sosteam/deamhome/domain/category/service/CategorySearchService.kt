package sosteam.deamhome.domain.category.service

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.category.dto.ItemCategoryDTO2
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository

@Service
class CategorySearchService (
    private val itemCategoryRepository: ItemCategoryRepository
){

    //TODO 에러처리해라
    suspend fun findCategoryByTitle(title: String) : ItemCategoryDTO2 {
        val itemCategory = itemCategoryRepository.findByTitle(title)
//            ?: return ItemCategoryDTO2(title = "not found", itemDetailCategories = mutableListOf())

        return ItemCategoryDTO2(
            title = itemCategory?.title ?: "not found",
            itemDetailCategories = itemCategory?.itemDetailCategories ?: mutableListOf()
        )
    }

    //TODO 비어있으면 에러처리
    fun getItemCategoriesContainsTitle(title: String): Flow<ItemCategoryDTO2>{
        return itemCategoryRepository.getItemCategoriesContainsTitle(title)
    }

//    fun getItemsContainsTitle(title: String): Flow<ItemDTO>{
//        return itemCategoryRepository.getItemsContainsTitle(title)
//    }
}