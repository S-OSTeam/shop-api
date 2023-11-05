package sosteam.deamhome.domain.item.service

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.item.entity.dto.ItemDTO
import sosteam.deamhome.domain.item.repository.ItemRepository

@Service
@Transactional
class ItemSearchService(
    private val itemRepository: ItemRepository
) {

    fun getItemsContainsTitle(title: String): Flow<ItemDTO>{
        return itemRepository.getItemsContainsTitle(title)
    }
}