package sosteam.deamhome.domain.order.service.order

import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.order.entity.Order
import sosteam.deamhome.domain.order.handler.request.OrderReadRequest
import sosteam.deamhome.domain.order.handler.response.OrderInfoResponse
import sosteam.deamhome.domain.order.repository.OrderRepository
import sosteam.deamhome.global.attribute.OrderStatus

@Service
class OrderReadService (
    private val orderRepository: OrderRepository,

){
    // Order list 가져오기
    suspend fun getOrder(userId: String, request: OrderReadRequest?): List<OrderInfoResponse>{

        val orderResponses = mutableListOf<OrderInfoResponse>()
        var orders: List<Order>

        if (request != null) {
            orders = orderRepository.findAllByUserIdAndCreatedAtBetween(userId, request.start, request.end).toList()
        } else {
            orders = orderRepository.findAllByUserId(userId).toList()
        }

        for (order in orders) {
            val orderResponse: OrderInfoResponse = OrderInfoResponse.fromOrder(order)
            orderResponses.add(orderResponse)
        }

        return orderResponses


    }

    // 주문 상태별 order list 가져오기
    suspend fun getOrderByStatus(userId: String, orderStatus: OrderStatus): List<OrderInfoResponse>{
        val orderResponses = mutableListOf<OrderInfoResponse>()
        val orders: List<Order> = orderRepository.findAllByUserIdAndOrderStatus(userId, orderStatus).toList()

        for (order in orders) {
            val orderResponse: OrderInfoResponse = OrderInfoResponse.fromOrder(order)
            orderResponses.add(orderResponse)
        }
        return orderResponses
    }
}