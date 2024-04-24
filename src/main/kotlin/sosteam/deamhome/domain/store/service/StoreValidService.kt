package sosteam.deamhome.domain.store.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.store.exception.StoreNameAlreadyExistException
import sosteam.deamhome.domain.store.repository.StoreRepository

@Service
class StoreValidService (
    private val storeRepository: StoreRepository
){
    // storeName 이미 존재하는지 체크
    suspend fun isStoreNameExist(storeName: String): Boolean{

        if(storeRepository.findStoreByStoreName(storeName) == null){
            return true
        }else{
            throw StoreNameAlreadyExistException()
        }
    }

}