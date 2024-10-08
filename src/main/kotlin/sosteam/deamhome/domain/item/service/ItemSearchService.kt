package sosteam.deamhome.domain.item.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.entity.ItemCategory
import sosteam.deamhome.domain.item.exception.ItemNotFoundException
import sosteam.deamhome.domain.item.handler.request.ItemSearchRequest
import sosteam.deamhome.domain.item.handler.response.ItemResponse
import sosteam.deamhome.domain.item.repository.ItemCategoryRepository
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.global.category.exception.CategoryNotFoundException

@Service
@Transactional
class ItemSearchService(
	private val itemRepository: ItemRepository,
	private val itemCategoryRepository: ItemCategoryRepository
) {
	
	// categoryPublicId 로 하위 카테고리들의 아이템을 반환하는 함수
	suspend fun findItemsByCategoryPublicId(categoryPublicId: String): List<ItemResponse> {
		// 카테고리가 있는지 확인
		val itemCategory = itemCategoryRepository.findByPublicId(categoryPublicId)
			?: throw CategoryNotFoundException()
		// 하위 카테고리들의 아이템들 찾음
		return findItemsInCategoryAndDescendants(itemCategory)
			.map { ItemResponse.fromItem(it) }
	}
	
	suspend fun findItemByPublicId(publicId: String): ItemResponse {
		val item = itemRepository.findByPublicId(publicId)
			?: throw ItemNotFoundException()
		return ItemResponse.fromItem(item)
	}
	
	// publicId 리스트를 받아 각 아이템 정보 찾아서 ItemResponse 리스트를 반환하는 함수
	suspend fun findItemByPublicIdIn(
		publicIdList: List<String>,
		page: Int,
		pageSize: Int = 10
	): List<ItemResponse> {
		val pageRequest: PageRequest = PageRequest.of(page, pageSize, Sort.by(Sort.Order.desc("created_at")))
		return itemRepository.findByPublicIdIn(publicIdList, pageRequest)
			.toList()
			.map { ItemResponse.fromItem(it) }
	}
	
	// 페이지네이션 없이
	suspend fun findItemByPublicIdIn(publicIdList: List<String>): List<ItemResponse> {
		return itemRepository.findByPublicIdIn(publicIdList)
			.toList()
			.map { ItemResponse.fromItem(it) }
	}
	
	// 하위의 카테고리에 있는 아이템을 반환하는 함수
	private suspend fun findItemsInCategoryAndDescendants(category: ItemCategory): List<Item> {
		val parentIds: MutableList<String> = mutableListOf()
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
	
	// title, sort 로 검색하는 경우 (검색창에서 검색)
	suspend fun searchItem(request: ItemSearchRequest): Flow<ItemResponse> {
		return itemRepository.searchItem(request)
			.map { ItemResponse.fromItem(it) }
	}
	
	//storeName 해당하는 item PublicId 리스트 반환
	suspend fun searchPublicIdByStore(storeName: String): List<String> {
		val items = itemRepository.findByStoreId(storeName).toList()
		return items.map { it.publicId }
	}
}