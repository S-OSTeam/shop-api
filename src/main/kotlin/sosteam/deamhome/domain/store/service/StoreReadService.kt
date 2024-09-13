package sosteam.deamhome.domain.store.service

import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.item.handler.response.ItemResponse
import sosteam.deamhome.domain.item.service.ItemSearchService
import sosteam.deamhome.domain.store.handler.response.StoreResponse
import sosteam.deamhome.domain.store.repository.StoreRepository

@Service
class StoreReadService(
	private val storeValidService: StoreValidService,
	private val storeRepository: StoreRepository,
	private val itemSearchService: ItemSearchService,
) {
	suspend fun getStore(storeName: String): StoreResponse {
		val store = storeValidService.findStoreByStoreName(storeName)
		return StoreResponse.fromStore(store)
	}
	
	suspend fun getStoreList(): List<StoreResponse> {
		val stores = storeRepository.findAll().toList()
		val storeList = mutableListOf<StoreResponse>()
		
		for (store in stores) {
			val storeResponse: StoreResponse = StoreResponse.fromStore(store)
			storeList.add(storeResponse)
		}
		return storeList
	}
	
	// 스토어 판매 아이템 리스트
	suspend fun getStoreItemList(storeName: String): List<ItemResponse> {
		val itemIds = itemSearchService.searchPublicIdByStore(storeName)
		return itemSearchService.findItemByPublicIdIn(itemIds)
	}
	
}