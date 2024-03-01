package sosteam.deamhome.domain.order.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.order.entity.Order
import sosteam.deamhome.domain.order.entity.QOrder
import sosteam.deamhome.domain.order.repository.custom.OrderRepositoryCustom
import sosteam.deamhome.domain.order.repository.querydsl.OrderQuerydslRepository

@GraphQlRepository
class OrderRepositoryImpl(val querydsl: OrderQuerydslRepository) : OrderRepositoryCustom {
	val order = QOrder.order
	
	override fun getOrdersByAccountAndNotFinished(accountId: String): Flow<Order> {
		return flowOf()
		// TODO: 민호 코드 수정 필요
//		return querydsl.findAll(
//			order.accountId.eq(accountId)
//				.and(order.orderStatus.eq(OrderStatus.PENDING).or(order.orderStatus.eq(OrderStatus.TRANSIT)))
//		).asFlow()
	}
}