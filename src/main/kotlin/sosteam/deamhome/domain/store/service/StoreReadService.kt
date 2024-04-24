package sosteam.deamhome.domain.store.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.store.handler.response.StoreResponse

@Service
class StoreReadService(
    private val storeValidService: StoreValidService,
) {
    suspend fun getStore(storeName: String): StoreResponse{
        val store = storeValidService.findStoreByStoreName(storeName)
        return StoreResponse.fromStore(store)
    }
}