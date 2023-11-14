package sosteam.deamhome.domain.item.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.item.repository.ItemRepository

@Service
class ItemDeleteService (private val itemRepository: ItemRepository) {

    suspend fun deleteItem(id: String){
        itemRepository.deleteItemById(id)
    }
}