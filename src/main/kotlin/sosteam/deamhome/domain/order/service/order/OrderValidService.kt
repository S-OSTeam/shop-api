package sosteam.deamhome.domain.order.service.order

import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.order.entity.Order
import sosteam.deamhome.domain.order.exception.OrderNotFoundException
import sosteam.deamhome.domain.order.exception.OrderWaitDeliveredException
import sosteam.deamhome.domain.order.repository.OrderRepository

@Service
class OrderValidService(
	private val orderRepository: OrderRepository
) {

	suspend fun isNotExistLiveOrder(accountId: String): Boolean {
		val orders = orderRepository.getOrdersByAccountAndNotFinished(accountId).toList()

		if (orders.isNotEmpty())
			throw OrderWaitDeliveredException()
		return true
	}

	suspend fun getOrderByPublicId(publicId: String): Order {
		return orderRepository.findOrderByPublicId(publicId)
			?: throw OrderNotFoundException()

	}
}