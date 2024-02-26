package sosteam.deamhome.domain.event.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.event.exception.InvalidEventTimeException
import sosteam.deamhome.domain.event.handler.request.EventRequest
import sosteam.deamhome.domain.event.handler.response.EventInfoResponse
import sosteam.deamhome.domain.event.repository.EventRepository
import sosteam.deamhome.global.image.provider.ImageProvider

@Service
class EventCreateService (
    private val eventRepository: EventRepository,
    private val imageProvider: ImageProvider,
){
    suspend fun createEvent(request: EventRequest): EventInfoResponse{

        // 이벤트 시작 날짜가 종료 날짜보다 앞인지 확인
        if (request.started.isAfter(request.ended)) {
            throw InvalidEventTimeException()
        }

        val event = request.asDomain().apply{
            // 이미지 저장
            images = request.images?.map{
                imageProvider.saveImage(it.image, it.outer, it.inner, it.resizeWidth, it.resizeHeight)
                    .fileUrl
            }?.toMutableList() ?: mutableListOf()
        }
        val saved = eventRepository.save(event)
        return EventInfoResponse.fromEvent(saved)
    }
}