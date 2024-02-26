package sosteam.deamhome.domain.event.handler.response

import sosteam.deamhome.domain.event.entity.Event
import java.time.LocalDateTime

// 리스트 안 아이템 간략 response
class EventItemResponse (
    val id: Long?,
    val started: LocalDateTime,
    val ended: LocalDateTime,
    val title: String,
    val contents: String?,
    val thumbnail: String?,

){
    companion object{
        fun fromEvent(event: Event): EventItemResponse{
            return EventItemResponse(
                id= event.id,
                started = event.started,
                ended = event.ended,
                title = event.title,
                contents = event.contents,
                thumbnail = event.thumbnail,
            )
        }
    }
}