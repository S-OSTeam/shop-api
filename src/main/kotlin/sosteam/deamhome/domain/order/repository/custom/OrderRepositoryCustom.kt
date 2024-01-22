package sosteam.deamhome.domain.order.repository.custom

import kotlinx.coroutines.flow.Flow
import sosteam.deamhome.domain.order.entity.Order

interface OrderRepositoryCustom {
	fun getOrdersByAccountAndNotFinished(accountId: String): Flow<Order>
}