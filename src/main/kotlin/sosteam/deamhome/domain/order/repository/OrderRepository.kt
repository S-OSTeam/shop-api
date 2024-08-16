package sosteam.deamhome.domain.order.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.order.entity.Order
import sosteam.deamhome.domain.order.repository.custom.OrderRepositoryCustom
import sosteam.deamhome.domain.order.entity.OrderStatus
import java.time.OffsetDateTime

@GraphQlRepository
interface OrderRepository : CoroutineCrudRepository<Order, Long>, OrderRepositoryCustom {
	fun findAllByUserIdAndCreatedAtBetween(userId: String, start: OffsetDateTime, end: OffsetDateTime): Flow<Order>
	fun findAllByUserId(userId: String): Flow<Order>
	fun findAllByUserIdAndOrderStatus(userId: String, orderStatus: OrderStatus): Flow<Order>
	fun findAllByUserIdAndOrderStatusAndCreatedAtBetween(
		userId: String,
		orderStatus: OrderStatus,
		start: OffsetDateTime,
		end: OffsetDateTime
	)
			: Flow<Order>
	
	suspend fun findOrderByPublicId(publicId: String): Order?
}