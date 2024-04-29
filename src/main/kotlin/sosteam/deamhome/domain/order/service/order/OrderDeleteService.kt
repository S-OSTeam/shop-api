package sosteam.deamhome.domain.order.service.order

import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.order.entity.Order
import sosteam.deamhome.domain.order.entity.OrderedItem
import sosteam.deamhome.domain.order.repository.OrderRepository
import sosteam.deamhome.domain.order.repository.OrderedItemRepository

@Service
class OrderDeleteService(
    private val orderValidService: OrderValidService,
    private val orderedItemRepository: OrderedItemRepository,
    private val orderRepository: OrderRepository,
) {
    // 주문 삭제
    @Transactional
    suspend fun deleteOrder(orderId: String){
        val order: Order = orderValidService.getOrderByPublicId(orderId)

        deleteOrderedItems(orderId)
        orderRepository.delete(order)
    }

    // 연관 주문 아이템 삭제
    private suspend fun deleteOrderedItems(orderId: String){
        val orderedItems: List<OrderedItem> =
            orderedItemRepository.findAllByOrderId(orderId).toList()

        for(item in orderedItems){
            orderedItemRepository.delete(item)
        }
    }
}