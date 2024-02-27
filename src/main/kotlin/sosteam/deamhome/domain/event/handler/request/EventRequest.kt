package sosteam.deamhome.domain.event.handler.request

import sosteam.deamhome.domain.event.entity.Event
import sosteam.deamhome.global.entity.DTO
import sosteam.deamhome.global.image.handler.request.ImageRequest
import java.time.LocalDateTime

// 이벤트 생성 요청 Request
data class EventRequest (

    val startedAt: LocalDateTime,
    val endedAt: LocalDateTime,
    val title: String,
    val contents: String?,
    val thumbnail: String?,
    val items: MutableList<String>?,
    val images: List<ImageRequest>?,
): DTO {
    override fun asDomain(): Event {
       return Event(
           id = null,
           startedAt = startedAt,
           endedAt = endedAt,
           title = title,
           contents = contents,
           thumbnail = thumbnail,
           items = items ?: mutableListOf() ,
       )
    }
}