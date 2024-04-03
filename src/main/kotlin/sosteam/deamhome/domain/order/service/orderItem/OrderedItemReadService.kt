package sosteam.deamhome.domain.order.service.orderItem

import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.domain.order.entity.OrderedItem
import sosteam.deamhome.domain.order.handler.response.OrderInfoResponse
import sosteam.deamhome.domain.order.handler.response.OrderedItemResponse
import sosteam.deamhome.domain.order.repository.OrderedItemRepository

@Service
class OrderedItemReadService (
    private val orderedItemRepository: OrderedItemRepository,
    private val itemRepository: ItemRepository,
){
    // 주문에 속한 아이템 리턴
    suspend fun getOrderedItem(orderId: String): List<OrderedItemResponse>{

        val orderedItemResponses = mutableListOf<OrderedItemResponse>()
        val orderedItems: List<OrderedItem> = orderedItemRepository.findAllByOrderId(orderId).toList()

        // 주문에 속한 아이템 찾아서 response 추가
        for (orderedItem in orderedItems) {
            val item: Item? = itemRepository.findByPublicId(orderedItem.itemId)
            val orderedItemResponse: OrderedItemResponse =
                OrderedItemResponse.fromItem(item, orderedItem.count)

            orderedItemResponses.add(orderedItemResponse)
        }

        return orderedItemResponses
    }
}