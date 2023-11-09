package sosteam.deamhome.domain.item.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.item.entity.dto.ItemDTO
import sosteam.deamhome.domain.item.repository.ItemRepository

@Service
class ItemSearchService(
    private val itemRepository: ItemRepository,
    private val categoryRepository: ItemCategoryRepository
) {

    fun getItemsContainsTitle(title: String): Flow<ItemDTO>{
        return itemRepository.getItemsContainsTitle(title)
    }

    suspend fun getItemsByOption(categoryTitle: String?, detailTitle: String?, itemTitle: String?): Flow<ItemDTO>{
        val itemIds =
            if (!detailTitle.isNullOrBlank()) {
                categoryRepository.getItemIdsByItemDetailCategoryTitle(detailTitle)
            } else if (!categoryTitle.isNullOrBlank()) {
                categoryRepository.getItemIdsByCategoryTitle(categoryTitle)
            } else {
                null
            }

        val itemIdList = itemIds?.toList()

        println(itemIdList)

        return itemRepository.getItemsByOption(itemIdList, itemTitle)
    }
}