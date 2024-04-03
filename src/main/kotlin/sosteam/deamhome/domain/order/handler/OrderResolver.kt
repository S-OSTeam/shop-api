package sosteam.deamhome.domain.order.handler

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.order.handler.request.OrderRequest
import sosteam.deamhome.domain.order.handler.response.OrderInfoResponse
import sosteam.deamhome.domain.order.service.order.OrderCreateService
import sosteam.deamhome.domain.order.service.order.OrderReadService
import sosteam.deamhome.global.security.service.AuthenticationService

@RestController
class OrderResolver(
    private val orderCreateService: OrderCreateService,
    private val authenticationService: AuthenticationService,
    private val orderReadService: OrderReadService,
) {
    //현재 사용자 장바구니 기준으로 주문 생성
    @MutationMapping
    suspend fun createOrder(@Argument request: OrderRequest): OrderInfoResponse{
        return orderCreateService.createOrder(request, authenticationService.getUserIdFromToken())
    }

    // order 조회
    @QueryMapping
    suspend fun getOrders(): List<OrderInfoResponse>{
        return orderReadService.getOrder(authenticationService.getUserIdFromToken())
    }
}