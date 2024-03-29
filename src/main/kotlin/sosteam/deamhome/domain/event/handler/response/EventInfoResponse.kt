package sosteam.deamhome.domain.event.handler.response

import sosteam.deamhome.domain.event.entity.Event
import java.time.LocalDateTime
import java.time.OffsetDateTime

// 전체 정보 response
class EventInfoResponse (
    val id: Long?,
    val startedAt: OffsetDateTime,
    val endedAt: OffsetDateTime,
    val title: String,
    val contents: String?,
    val thumbnail: String?,
    val items: MutableList<String>,
    val images: MutableList<String>,
    val link: String?
){
    companion object{
        fun fromEvent(event: Event): EventInfoResponse{
            return EventInfoResponse(
                id= event.id,
                startedAt = event.startedAt,
                endedAt = event.endedAt,
                title = event.title,
                contents = event.contents,
                thumbnail = event.thumbnail,
                items = event.items,
                images = event.images,
                link = event.link,
            )
        }
    }
}