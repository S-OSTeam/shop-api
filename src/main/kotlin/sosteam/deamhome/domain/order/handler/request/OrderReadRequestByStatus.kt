package sosteam.deamhome.domain.order.handler.request

import sosteam.deamhome.domain.order.entity.OrderStatus
import java.time.OffsetDateTime

data class OrderReadRequestByStatus(
	val start: OffsetDateTime?,
	val end: OffsetDateTime?,
	val orderStatus: OrderStatus,
)