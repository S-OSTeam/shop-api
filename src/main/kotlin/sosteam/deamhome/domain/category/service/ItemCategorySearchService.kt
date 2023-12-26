package sosteam.deamhome.domain.category.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.category.handler.response.ItemCategoryResponse
import sosteam.deamhome.domain.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.handler.response.ItemCategoryTreeResponse
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository

@Service
@Transactional
class ItemCategorySearchService (
    private val itemCategoryRepository: ItemCategoryRepository
) {

    suspend fun findItemCategoryByPublicId(publicId: Long): ItemCategoryResponse {
        val itemCategory = itemCategoryRepository.findByPublicId(publicId)
            ?: throw CategoryNotFoundException()
        return ItemCategoryResponse.fromItemCategory(itemCategory)
    }

    suspend fun findItemCategoryByTitle(title: String): ItemCategoryResponse {
        val itemCategory = itemCategoryRepository.findByTitle(title)
            ?: throw CategoryNotFoundException()
        return ItemCategoryResponse.fromItemCategory(itemCategory)
    }

    fun findItemCategoriesContainTitle(title: String): Flow<ItemCategoryResponse> {
        return itemCategoryRepository.findItemCategoriesContainTitle(title)
            .map { ItemCategoryResponse.fromItemCategory(it) }
    }

    fun findAllItemCategories(): Flow<ItemCategoryResponse> {
        return itemCategoryRepository.findAllItemCategories()
            .map { ItemCategoryResponse.fromItemCategory(it) }
    }

    suspend fun findAllItemCategoriesTree(): List<ItemCategoryTreeResponse> {
        val itemCategories = itemCategoryRepository.findAllItemCategories().toList()

        val map = mutableMapOf<Long, ItemCategoryTreeResponse>()

        itemCategories.forEach { itemCategory ->
            map[itemCategory.publicId] = ItemCategoryTreeResponse.fromItemCategory(itemCategory)
        }

        itemCategories.forEach { itemCategory ->
            itemCategory.parentPublicId?.let { parentPublicId ->
                val parent = map[parentPublicId]
                val child = map[itemCategory.publicId]
                //무지성 느낌표?
                parent!!.children!!.add(child!!)
            }
        }

        val roots = itemCategories
            .filter { it.parentPublicId == null }
            .map { map[it.publicId]!! }.toList()

        return roots
    }

}