package sosteam.deamhome.domain.store.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.store.repository.StoreRepository

@Service
class StoreDeleteService (
    private val storeValidService: StoreValidService,
    private val storeRepository: StoreRepository,
){

    suspend fun deleteStore(id: Long){
        val store = storeValidService.findStoreById(id)
        storeRepository.delete(store)
    }
}