package sosteam.deamhome.domain.item.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.item.repository.ItemRepository

@Service
class ItemDeleteService (private val itemRepository: ItemRepository) {

    //TODO 생각해보니까 DetailCategory의 itemIds 에서도 삭제해야함
    suspend fun deleteItem(id: String){
        itemRepository.deleteItemById(id)
    }
}