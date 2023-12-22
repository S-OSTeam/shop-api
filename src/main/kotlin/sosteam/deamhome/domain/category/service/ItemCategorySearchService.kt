package sosteam.deamhome.domain.category.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.handler.response.ItemCategoryResponse
import sosteam.deamhome.domain.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository

@Service
@Transactional
class ItemCategorySearchService (
    private val itemCategoryRepository: ItemCategoryRepository
) {

    suspend fun getItemCategoryByPublicId(publicId: Long): ItemCategoryResponse {
        val itemCategory = itemCategoryRepository.findByPublicId(publicId)
            ?: throw CategoryNotFoundException()
        return ItemCategoryResponse.fromItemCategory(itemCategory)
    }

    fun findItemCategoriesContainsTitle(title: String): Flow<ItemCategoryResponse> {
        return itemCategoryRepository.findItemCategoriesContainsTitle(title)
            .map { ItemCategoryResponse.fromItemCategory(it) }
    }

    suspend fun findAllItemCategories(): Flow<ItemCategoryResponse> {
        return itemCategoryRepository.findAllItemCategories()
            .map { ItemCategoryResponse.fromItemCategory(it) }
    }

}