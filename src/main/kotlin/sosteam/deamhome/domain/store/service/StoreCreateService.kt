package sosteam.deamhome.domain.store.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.store.entity.Store
import sosteam.deamhome.domain.store.handler.request.StoreRequest
import sosteam.deamhome.domain.store.handler.response.StoreResponse
import sosteam.deamhome.domain.store.repository.StoreRepository

@Service
class StoreCreateService(
    private val storeRepository: StoreRepository,
    private val storeValidService: StoreValidService,
) {
    // TODO: admin 계정에서만 제작하도록 제한해야함
    suspend fun createStore( request: StoreRequest): StoreResponse {

        // 이름 중복처리
        storeValidService.isStoreNameExist(request.storeName)

        val store = request.asDomain()
        val saved = storeRepository.save(store)
        return StoreResponse.fromStore(saved)
    }
}