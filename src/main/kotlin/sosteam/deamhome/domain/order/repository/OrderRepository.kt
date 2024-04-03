package sosteam.deamhome.domain.order.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.order.entity.Order
import sosteam.deamhome.domain.order.repository.custom.OrderRepositoryCustom

@GraphQlRepository
interface OrderRepository : CoroutineCrudRepository<Order, Long>, OrderRepositoryCustom{
    fun findAllByUserId(userId: String): Flow<Order>
    suspend fun findOrderByPublicId(publicId: String): Order?
}