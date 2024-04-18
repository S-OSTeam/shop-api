package sosteam.deamhome.domain.order.service.order

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.auth.exception.AccountInvalidExeption
import sosteam.deamhome.domain.order.entity.Order
import sosteam.deamhome.domain.order.handler.request.OrderUpdateRequest
import sosteam.deamhome.domain.order.repository.OrderRepository

@Service
class OrderUpdateService (
    private val orderRepository: OrderRepository,
    private val orderValidService: OrderValidService,
){
    // 주문 개인정보 변경
    // 주소, 현관문 비번, 주문 요청사항, 메모
    suspend fun updateOrder(userId: String, request: OrderUpdateRequest){


        val order: Order = orderValidService.getOrderByPublicId(request.orderId)
        if(userId != order.userId){ // 접속한 사용자 userID와 주문자 userID 다름
            throw AccountInvalidExeption()
        }

        val updatedOrder = request.asOrder(order)

        orderRepository.save(updatedOrder)
    }
}