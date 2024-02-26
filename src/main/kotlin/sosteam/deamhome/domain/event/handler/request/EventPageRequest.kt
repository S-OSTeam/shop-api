package sosteam.deamhome.domain.event.handler.request

data class EventPageRequest (
    val eventId: Long,
    val page: Int =0,
    val pageSize: Int
)