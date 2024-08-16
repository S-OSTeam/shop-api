package sosteam.deamhome.domain.order.service.order

import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.item.service.ItemSearchService
import sosteam.deamhome.domain.order.entity.Order
import sosteam.deamhome.domain.order.handler.request.OrderReadRequest
import sosteam.deamhome.domain.order.handler.request.OrderReadRequestByStatus
import sosteam.deamhome.domain.order.handler.response.OrderInfoResponse
import sosteam.deamhome.domain.order.repository.OrderRepository

@Service
class OrderReadService(
	private val orderRepository: OrderRepository,
	private val itemSearchService: ItemSearchService,
	
	) {
	// Order list 가져오기
	suspend fun getOrder(userId: String, request: OrderReadRequest?): List<OrderInfoResponse> {
		
		val orderResponses = mutableListOf<OrderInfoResponse>()
		var orders: List<Order>
		
		if (request != null) {// 기간 설정
			orders = orderRepository.findAllByUserIdAndCreatedAtBetween(userId, request.start, request.end).toList()
		} else { // 전체 조회
			orders = orderRepository.findAllByUserId(userId).toList()
		}
		
		for (order in orders) {
			val orderResponse: OrderInfoResponse = OrderInfoResponse.fromOrder(order)
			orderResponses.add(orderResponse)
		}
		
		return orderResponses
		
		
	}
	
	// 주문 상태별 order list 가져오기
	suspend fun getOrderByStatus(userId: String, request: OrderReadRequestByStatus): List<OrderInfoResponse> {
		val (start, end, orderStatus) = request
		
		val orderResponses = mutableListOf<OrderInfoResponse>()
		var orders: List<Order>
		
		
		if (start != null && end != null) { // 기간 설정
			orders = orderRepository.findAllByUserIdAndOrderStatusAndCreatedAtBetween(
				userId,
				orderStatus,
				start,
				end
			).toList()
		} else { // 전체 조회
			orders = orderRepository.findAllByUserIdAndOrderStatus(userId, orderStatus).toList()
		}
		
		for (order in orders) {
			val orderResponse: OrderInfoResponse = OrderInfoResponse.fromOrder(order)
			orderResponses.add(orderResponse)
		}
		return orderResponses
	}
	
	// 스토어별 주문 가져오기 Todo: ( userId 받은 후 storeId로 변경하기..)
	suspend fun getOrderByStore(storeId: Long, request: OrderReadRequestByStatus): List<OrderInfoResponse> {
		val (start, end, orderStatus) = request
		val orderResponses = mutableListOf<OrderInfoResponse>()
		
		
		return orderResponses
	}
}