package sosteam.deamhome.domain.store.handler

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.item.handler.response.ItemResponse
import sosteam.deamhome.domain.store.handler.request.StoreRequest
import sosteam.deamhome.domain.store.handler.response.StoreResponse
import sosteam.deamhome.domain.store.service.StoreCreateService
import sosteam.deamhome.domain.store.service.StoreDeleteService
import sosteam.deamhome.domain.store.service.StoreReadService

@RestController
class StoreResolver(
	private val storeCreateService: StoreCreateService,
	private val storeDeleteService: StoreDeleteService,
	private val storeReadService: StoreReadService,
) {
	// Todo: admin 계정에서만 생성 가능하게 변경..
	@MutationMapping
	suspend fun createStore(@Argument request: StoreRequest): StoreResponse {
		return storeCreateService.createStore(request)
	}
	
	// Todo: admin 계정에서만 삭제 가능하게 변경
	@MutationMapping
	suspend fun deleteStore(@Argument id: Long) {
		storeDeleteService.deleteStore(id)
	}
	
	@QueryMapping
	suspend fun getStore(@Argument storeName: String): StoreResponse {
		return storeReadService.getStore(storeName)
	}
	
	@QueryMapping
	suspend fun getStoreList(): List<StoreResponse> {
		return storeReadService.getStoreList()
	}
	
	@QueryMapping
	suspend fun getStoreItemList(@Argument storeName: String): List<ItemResponse> {
		return storeReadService.getStoreItemList(storeName)
	}
}