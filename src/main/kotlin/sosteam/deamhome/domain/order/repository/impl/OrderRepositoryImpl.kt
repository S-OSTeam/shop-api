package sosteam.deamhome.domain.order.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.order.entity.Order
import sosteam.deamhome.domain.order.entity.QOrder
import sosteam.deamhome.domain.order.repository.custom.OrderRepositoryCustom
import sosteam.deamhome.domain.order.repository.querydsl.OrderQuerydslRepository
import sosteam.deamhome.global.attribute.OrderStatus

@GraphQlRepository
class OrderRepositoryImpl(val querydsl: OrderQuerydslRepository) : OrderRepositoryCustom {
	private val order = QOrder.order
	
	override fun getOrdersByAccountAndNotFinished(accountId: String): Flow<Order> {
		return querydsl.findAll(
			order.userId.eq(accountId)
				.and(order.orderStatus.stringValue().eq(OrderStatus.PENDING.name).or(order.orderStatus.stringValue().eq(OrderStatus.TRANSIT.name)))
		).asFlow()
	}
}