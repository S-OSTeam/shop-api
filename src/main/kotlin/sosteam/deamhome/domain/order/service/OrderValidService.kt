package sosteam.deamhome.domain.order.service

import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
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


}