package sosteam.deamhome.domain.item.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.global.category.exception.CategoryNotFoundException
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
    // title 이 포함된 아이템 검색
    fun findItemsContainTitle(title: String): Flow<ItemResponse> {
        return itemRepository.findItemsContainTitle(title)
            .map { ItemResponse.fromItem(it) }
    }

    // categoryTitle 로 하위 카테고리들의 아이템을 반환하는 함수
    suspend fun findItemsByCategoryTitle(categoryTitle: String): List<ItemResponse> {
        // 카테고리가 있는지 확인
        val itemCategory = itemCategoryRepository.findByTitle(categoryTitle)
            ?: throw CategoryNotFoundException()
        // 하위 카테고리들의 아이템들 찾음
        return findItemsInCategoryAndDescendants(itemCategory)
            .map { ItemResponse.fromItem(it) }
    }

    // categoryPublicId 로 하위 카테고리들의 아이템을 반환하는 함수
    suspend fun findItemsByCategoryPublicId(categoryPublicId: Long): List<ItemResponse> {
        // 카테고리가 있는지 확인
        val itemCategory = itemCategoryRepository.findByPublicId(categoryPublicId)
            ?: throw CategoryNotFoundException()
        // 하위 카테고리들의 아이템들 찾음
        return findItemsInCategoryAndDescendants(itemCategory)
            .map { ItemResponse.fromItem(it) }
    }

    suspend fun findItemByPublicId(publicId: Long): ItemResponse {
        val item = itemRepository.findByPublicId(publicId)
            ?: throw ItemNotFoundException()
        return ItemResponse.fromItem(item)
    }

    // 하위의 카테고리에 있는 아이템을 반환하는 함수
    private suspend fun findItemsInCategoryAndDescendants(category: ItemCategory): List<Item>{
        val parentIds: MutableList<Long> = mutableListOf()
        parentIds.add(category.publicId)
        // category.publicId 로 하위 카테고리를 찾고 parentIds 에 추가
        val childIds = itemCategoryRepository.findByParentPublicId(category.publicId).toList()
            .onEach { parentIds.add(it.publicId) }
            .map { it.publicId }
        // childIds 로 하위 카테고리를 찾고 parentIds 에 추가
        parentIds.addAll(itemCategoryRepository.findByParentPublicIdIn(childIds).toList().map { it.publicId })
        // parentIds 로 Item 을 찾음
        return itemRepository.findByCategoryPublicIdIn(parentIds).toList()
    }


}