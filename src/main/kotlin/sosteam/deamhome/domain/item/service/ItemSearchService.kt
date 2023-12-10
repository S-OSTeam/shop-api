package sosteam.deamhome.domain.item.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.item.entity.dto.response.ItemResponseDTO
import sosteam.deamhome.domain.item.repository.ItemRepository

@Service
class ItemSearchService(
    private val itemRepository: ItemRepository,
    private val categoryRepository: ItemCategoryRepository
) {

    fun getItemsContainsTitle(title: String): Flow<ItemResponseDTO>{
        return itemRepository.getItemsContainsTitle(title)
            .map { ItemResponseDTO.fromItem(it) }
    }

    suspend fun getItemsByOption(categoryTitle: String?, detailTitle: String?, itemTitle: String?): Flow<ItemResponseDTO>{
        val itemIds =
            if (!detailTitle.isNullOrBlank()) {
                categoryRepository.getItemIdsByItemDetailCategoryTitle(detailTitle)
            } else if (!categoryTitle.isNullOrBlank()) {
                categoryRepository.getItemIdsByCategoryTitle(categoryTitle)
            } else {
                null
            }

        val itemIdList = itemIds?.toList()

        return itemRepository.getItemsByOption(itemIdList, itemTitle)
            .map { ItemResponseDTO.fromItem(it) }
    }
}