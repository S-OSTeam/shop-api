package sosteam.deamhome.domain.order.handler

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.order.handler.request.OrderReadRequest
import sosteam.deamhome.domain.order.handler.request.OrderReadRequestByStatus
import sosteam.deamhome.domain.order.handler.request.OrderRequest
import sosteam.deamhome.domain.order.handler.request.OrderUpdateRequest
import sosteam.deamhome.domain.order.handler.response.OrderInfoResponse
import sosteam.deamhome.domain.order.handler.response.OrderedItemResponse
import sosteam.deamhome.domain.order.service.order.OrderCreateService
import sosteam.deamhome.domain.order.service.order.OrderDeleteService
import sosteam.deamhome.domain.order.service.order.OrderReadService
import sosteam.deamhome.domain.order.service.order.OrderUpdateService
import sosteam.deamhome.domain.order.service.orderItem.OrderedItemReadService
import sosteam.deamhome.domain.payment.exception.UnauthorizedSessionException
import sosteam.deamhome.domain.payment.verifier.PaymentVerifier
import sosteam.deamhome.global.attribute.Token
import sosteam.deamhome.global.provider.RequestProvider.Companion.getToken
import sosteam.deamhome.global.security.provider.JWTProvider
import sosteam.deamhome.global.security.service.AuthenticationService

@RestController
class OrderResolver(
	private val orderCreateService: OrderCreateService,
	private val authenticationService: AuthenticationService,
	private val jwtProvider: JWTProvider,
	private val orderReadService: OrderReadService,
	private val orderedItemReadService: OrderedItemReadService,
	private val orderDeleteService: OrderDeleteService,
	private val orderUpdateService: OrderUpdateService,
	private val paymentVerifier: PaymentVerifier
) {
	//현재 사용자 장바구니 기준으로 주문 생성
	@MutationMapping
	suspend fun createOrder(@Argument request: OrderRequest): OrderInfoResponse {
		val token = getToken()
		if (!paymentVerifier.isVerified())
			throw UnauthorizedSessionException()
		return orderCreateService.createOrder(request, jwtProvider.getUserId(token, Token.ACCESS))
	}
	
	// 주문 삭제
	@MutationMapping
	suspend fun deleteOrder(@Argument orderId: String) {
		return orderDeleteService.deleteOrder(orderId)
	}
	
	// 주문 수정
	@MutationMapping
	suspend fun updateOrder(@Argument request: OrderUpdateRequest) {
		val token = getToken()
		return orderUpdateService.updateOrder(jwtProvider.getUserId(token, Token.ACCESS), request)
	}
	
	// 주문 조회
	@QueryMapping
	suspend fun getOrders(@Argument request: OrderReadRequest?): List<OrderInfoResponse> {
		val token = getToken()
		return orderReadService.getOrder(jwtProvider.getUserId(token, Token.ACCESS), request)
	}
	
	// 항목별 주문조회
	@QueryMapping
	suspend fun getOrdersByStatus(@Argument request: OrderReadRequestByStatus): List<OrderInfoResponse> {
		val token = getToken()
		return orderReadService.getOrderByStatus(jwtProvider.getUserId(token, Token.ACCESS), request)
	}
	
	
	// 주문 아이템 조회
	@QueryMapping
	suspend fun getOrderedItems(@Argument orderId: String): List<OrderedItemResponse> {
		return orderedItemReadService.getOrderedItem(orderId)
	}
}