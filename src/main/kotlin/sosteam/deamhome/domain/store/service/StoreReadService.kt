package sosteam.deamhome.domain.store.service

import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.store.handler.response.StoreResponse
import sosteam.deamhome.domain.store.repository.StoreRepository

@Service
class StoreReadService(
    private val storeValidService: StoreValidService,
    private val storeRepository: StoreRepository,
) {
    suspend fun getStore(storeName: String): StoreResponse{
        val store = storeValidService.findStoreByStoreName(storeName)
        return StoreResponse.fromStore(store)
    }

    suspend fun getStoreList(): List<StoreResponse>{
        val stores = storeRepository.findAll().toList()
        val storeList = mutableListOf<StoreResponse>()

        for (store in stores){
            val storeResponse : StoreResponse = StoreResponse.fromStore(store)
            storeList.add(storeResponse)
        }

        return storeList
    }

}