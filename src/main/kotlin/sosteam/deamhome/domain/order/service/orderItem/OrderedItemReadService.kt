package sosteam.deamhome.domain.order.service.orderItem

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.order.repository.OrderedItemRepository

@Service
class OrderedItemReadService (
    private val orderedItemRepository: OrderedItemRepository
){
}