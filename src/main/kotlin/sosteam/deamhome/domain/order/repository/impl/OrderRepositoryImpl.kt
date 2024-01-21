//package sosteam.deamhome.domain.order.repository.impl
//
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.reactive.asFlow
//import org.springframework.graphql.data.GraphQlRepository
//import sosteam.deamhome.domain.order.entity.Order
//import sosteam.deamhome.domain.order.entity.QOrder
//import sosteam.deamhome.domain.order.repository.custom.OrderRepositoryCustom
//import sosteam.deamhome.domain.order.repository.querydsl.OrderQuerydslRepository
//import sosteam.deamhome.global.attribute.OrderStatus
//
//@GraphQlRepository
//class OrderRepositoryImpl(val querydsl: OrderQuerydslRepository) : OrderRepositoryCustom {
//	val order = QOrder.order
//
//	override fun getOrdersByAccountAndNotFinished(accountId: String): Flow<Order> {
//		return querydsl.findAll(
//			order.accountId.eq(accountId)
//				.and(order.orderStatus.eq(OrderStatus.PENDING).or(order.orderStatus.eq(OrderStatus.TRANSIT)))
//		).asFlow()
//	}
//}