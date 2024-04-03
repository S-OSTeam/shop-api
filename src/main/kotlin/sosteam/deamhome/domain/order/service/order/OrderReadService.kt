package sosteam.deamhome.domain.order.service.order

import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.order.entity.Order
import sosteam.deamhome.domain.order.handler.response.OrderInfoResponse
import sosteam.deamhome.domain.order.repository.OrderRepository

@Service
class OrderReadService (
    private val orderRepository: OrderRepository,

){
    // Order list 가져오기
    suspend fun getOrder(userId: String): List<OrderInfoResponse>{
        val orderResponses = mutableListOf<OrderInfoResponse>()
        val orders: List<Order> = orderRepository.findAllByUserId(userId).toList()

        for (order in orders) {
            val orderResponse: OrderInfoResponse = OrderInfoResponse.fromOrder(order)
            orderResponses.add(orderResponse)
        }
        return orderResponses
    }
}