package sosteam.deamhome.domain.event.service

import kotlinx.coroutines.flow.toList
import org.springframework.data.r2dbc.repository.Query
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.event.entity.Event
import sosteam.deamhome.domain.event.entity.EventType
import sosteam.deamhome.domain.event.handler.request.EventFilter
import sosteam.deamhome.domain.event.handler.request.EventPageRequest
import sosteam.deamhome.domain.event.handler.response.EventInfoResponse
import sosteam.deamhome.domain.event.repository.EventRepository
import sosteam.deamhome.domain.item.handler.response.ItemResponse
import sosteam.deamhome.domain.item.service.ItemSearchService
import java.time.LocalDateTime
import java.time.OffsetDateTime

@Service
class EventReadService(
	private val eventRepository: EventRepository,
	private val itemSearchService: ItemSearchService,
	private val eventValidService: EventValidService,
) {
	// 현재 진행중인 이벤트 리스트 리턴
	suspend fun getEventList(eventType: EventType): List<EventInfoResponse> {
		val eventList: List<Event> = eventRepository.findByEndedAtAfterAndEventType(OffsetDateTime.now(), eventType)
		return eventList.map { EventInfoResponse.fromEvent(it) }
	}
	
	// filter 검색을 통해 리스트 전체 리턴 ( title, contents, id, eventType)


	suspend fun getEventListInfo(filter: EventFilter): List<EventInfoResponse>  {
		val eventList: List<Event> = eventRepository
			.findEvent(
				filter
			).toList()
		return eventList.map { EventInfoResponse.fromEvent(it) }
	}
	
	// 이벤트 아이템 리스트 페이지네이션으로 가져오기
	suspend fun getEventItems(request: EventPageRequest): List<ItemResponse> {
		val (eventId, page, pageSize) = request
		// 해당 이벤트 찾기
		val event = eventValidService.getEventById(eventId)
		
		return itemSearchService.findItemByPublicIdIn(
			event.items, page, pageSize
		)
	}
}