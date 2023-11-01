package sosteam.deamhome.domain.category.service

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.category.dto.ItemCategoryDTO2
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.item.entity.dto.ItemDTO

@Service
@Transactional
class CategorySearchService (
    private val itemCategoryRepository: ItemCategoryRepository
){

    suspend fun findCategoryByTitle(title: String) : ItemCategoryDTO2 {
        val itemCategory = itemCategoryRepository.findByTitle(title)
            ?: return ItemCategoryDTO2(title = "not found", itemDetailCategories = mutableListOf())

        return ItemCategoryDTO2(
            title = title,
            itemDetailCategories = itemCategory.itemDetailCategories
        )
    }

    fun getItemCategoriesContainsTitle(title: String): Flow<ItemCategoryDTO2>{
        return itemCategoryRepository.getItemCategoriesContainsTitle(title)
    }

    fun getItemsContainsTitle(title: String): Flow<ItemDTO>{
        return itemCategoryRepository.getItemsContainsTitle(title)
    }
}