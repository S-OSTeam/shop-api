package sosteam.deamhome.domain.category.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.category.dto.response.ItemCategoryResponse
import sosteam.deamhome.domain.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository

@Service
@Transactional
class ItemCategorySearchService (
    private val itemCategoryRepository: ItemCategoryRepository
) {

    suspend fun getItemCategoryBySequence(sequence: Long): ItemCategoryResponse {
        val itemCategory = itemCategoryRepository.findBySequence(sequence)
            ?: throw CategoryNotFoundException()
        return ItemCategoryResponse.fromItemCategory(itemCategory)
    }

    fun findItemCategoriesContainsTitle(title: String): Flow<ItemCategoryResponse> {
        return itemCategoryRepository.findItemCategoriesContainsTitle(title)
            .map { ItemCategoryResponse.fromItemCategory(it) }
    }
}