package sosteam.deamhome.domain.order.handler.request

import java.time.OffsetDateTime

data class OrderReadRequest (
    val start: OffsetDateTime,
    val end: OffsetDateTime,
)