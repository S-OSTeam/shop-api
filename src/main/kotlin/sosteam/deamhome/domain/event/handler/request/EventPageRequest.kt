package sosteam.deamhome.domain.event.handler.request

import sosteam.deamhome.domain.event.entity.enum.EventType

data class EventPageRequest (
    val eventId: Long,
    val page: Int =0,
    val pageSize: Int,
    val eventType: EventType,
)