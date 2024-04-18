package sosteam.deamhome.domain.order.handler.request

import sosteam.deamhome.global.attribute.OrderStatus
import java.time.OffsetDateTime

data class OrderReadRequestByStatus (
    val start: OffsetDateTime?,
    val end: OffsetDateTime?,
    val orderStatus: OrderStatus,
)