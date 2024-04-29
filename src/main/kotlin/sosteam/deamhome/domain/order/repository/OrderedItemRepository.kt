package sosteam.deamhome.domain.order.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.order.entity.OrderedItem

@GraphQlRepository
interface OrderedItemRepository : CoroutineCrudRepository<OrderedItem, Long> {
    fun findAllByOrderId(orderId: String) : Flow<OrderedItem>
}