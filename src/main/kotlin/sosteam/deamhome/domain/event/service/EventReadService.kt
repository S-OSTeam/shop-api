package sosteam.deamhome.domain.event.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.event.exception.EventNotFoundException
import sosteam.deamhome.domain.event.handler.response.EventInfoResponse
import sosteam.deamhome.domain.event.handler.response.EventItemResponse
import sosteam.deamhome.domain.event.repository.EventRepository
import java.time.LocalDateTime

@Service
class EventReadService (
    private val eventRepository: EventRepository,

){
    // 현재 진행중인 이벤트 간략 리스트 리턴
    suspend fun getEventList():List<EventItemResponse>{
        val eventList = eventRepository.findByEndedAfter(LocalDateTime.now())
        return eventList.map{EventItemResponse.fromEvent(it) }
    }

    // id 기반으로 이벤트 전체 정보 리턴
    suspend fun getEventInfo(eventId: Long): EventInfoResponse{
        val event = eventRepository.findById(eventId)
            ?: throw EventNotFoundException()
        return EventInfoResponse.fromEvent(event)
    }
}