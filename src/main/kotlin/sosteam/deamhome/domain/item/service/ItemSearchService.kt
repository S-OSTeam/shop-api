package sosteam.deamhome.domain.item.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.exception.ItemNotFoundException
import sosteam.deamhome.domain.item.handler.response.ItemResponse
import sosteam.deamhome.domain.item.repository.ItemRepository

@Service
@Transactional
class ItemSearchService (
    private val itemRepository: ItemRepository,
    private val itemCategoryRepository: ItemCategoryRepository
){
    fun findItemsContainTitle(title: String): Flow<ItemResponse> {
        return itemRepository.findItemsContainTitle(title)
            .map { ItemResponse.fromItem(it) }
    }

    suspend fun findItemsByCategoryTitle(categoryTitle: String): List<ItemResponse> {
        val itemCategory = itemCategoryRepository.findByTitle(categoryTitle)
            ?: throw CategoryNotFoundException()

        return findItemsRecursive(itemCategory)
            .map { ItemResponse.fromItem(it) }
    }

    suspend fun findItemsByCategoryPublicId(categoryPublicId: Long): List<ItemResponse> {
        val itemCategory = itemCategoryRepository.findByPublicId(categoryPublicId)
            ?: throw CategoryNotFoundException()

        return findItemsRecursive(itemCategory)
            .map { ItemResponse.fromItem(it) }
    }

    suspend fun findItemByPublicId(publicId: Long): ItemResponse {
        val item = itemRepository.findByPublicId(publicId)
            ?: throw ItemNotFoundException()
        return ItemResponse.fromItem(item)
    }

    suspend fun findItemByTitle(itemTitle: String): ItemResponse {
        val item = itemRepository.findByTitle(itemTitle)
            ?: throw ItemNotFoundException()
        return ItemResponse.fromItem(item)
    }

    private suspend fun findItemsRecursive(category: ItemCategory): List<Item>{

        if (category.childrenPublicId.isEmpty()) {
            return itemRepository.findByCategoryPublicId(category.publicId).toList()
        }

        val itemList: MutableList<Item> = mutableListOf()

        for (childPublicId in category.childrenPublicId) {
            val childCategory = itemCategoryRepository.findByPublicId(childPublicId)
                ?: throw CategoryNotFoundException()
            itemList.addAll(findItemsRecursive(childCategory))
        }

        return itemList
    }


}