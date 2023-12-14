package sosteam.deamhome.domain.item.service

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.item.entity.dto.response.ItemResponseDTO
import sosteam.deamhome.domain.item.exception.ItemNotFoundException
import sosteam.deamhome.domain.item.repository.ItemRepository

@Service
class ItemUpdateService (
    private val itemRepository: ItemRepository
) {

    suspend fun increaseClickCnt(itemId: String): ItemResponseDTO {
        val item = itemRepository.findById(itemId).awaitSingleOrNull()
            ?: throw ItemNotFoundException()
        item.clickCnt += 1
        itemRepository.save(item).awaitSingleOrNull()
        return ItemResponseDTO.fromItem(item)
    }
}