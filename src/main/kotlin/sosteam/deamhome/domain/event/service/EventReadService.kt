package sosteam.deamhome.domain.event.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.event.exception.EventNotFoundException
import sosteam.deamhome.domain.event.handler.request.EventPageRequest
import sosteam.deamhome.domain.event.handler.response.EventInfoResponse
import sosteam.deamhome.domain.event.handler.response.EventItemResponse
import sosteam.deamhome.domain.event.repository.EventRepository
import sosteam.deamhome.domain.item.handler.response.ItemResponse
import sosteam.deamhome.domain.item.service.ItemSearchService
import java.time.LocalDateTime

@Service
class EventReadService (
    private val eventRepository: EventRepository,
    private val itemSearchService: ItemSearchService,
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

    // 이벤트 아이템 리스트 페이지네이션으로 가져오기
    suspend fun getEventItems(request: EventPageRequest): List<ItemResponse>{
        val (eventId, page, pageSize) = request
        // 해당 이벤트 찾기
        val event = eventRepository.findById(eventId)
            ?: throw EventNotFoundException()

        return itemSearchService.findItemByPublicIdIn(
            event.items,page, pageSize)
    }
}