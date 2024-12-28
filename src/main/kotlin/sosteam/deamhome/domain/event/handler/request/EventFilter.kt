package sosteam.deamhome.domain.event.handler.request

import sosteam.deamhome.domain.event.entity.EventType
import java.time.OffsetDateTime

data class EventFilter (
    val title: String?,
    val contents: String?,
    val id: Long?,
    val eventType: EventType?,
    val startedAt: OffsetDateTime?,
    val endedAt: OffsetDateTime?,
)
