package sosteam.deamhome.domain.event.handler

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.event.handler.request.EventPageRequest
import sosteam.deamhome.domain.event.handler.request.EventRequest
import sosteam.deamhome.domain.event.handler.response.EventInfoResponse
import sosteam.deamhome.domain.event.handler.response.EventItemResponse
import sosteam.deamhome.domain.event.service.EventCreateService
import sosteam.deamhome.domain.event.service.EventReadService
import sosteam.deamhome.domain.item.handler.response.ItemResponse

@RestController
class EventResolver (
    private val eventCreateService: EventCreateService,
    private val eventReadService: EventReadService,
){
    // 이벤트 생성
    @MutationMapping
    suspend fun createEvent(@Argument request: EventRequest): EventInfoResponse{
        return eventCreateService.createEvent(request)
    }

    // 현재 진행중인 이벤트 리스트 가져오기
    @QueryMapping
    suspend fun getEventList(): List<EventItemResponse>{
        return eventReadService.getEventList()
    }

    // eventId 이용해서 클릭 시 상세정보 조회
    @QueryMapping
    suspend fun getEventInfo(@Argument eventId: Long): EventInfoResponse{
        return eventReadService.getEventInfo(eventId)
    }

    // 이벤트 진행중인 아이템 publicId 매핑해서 리턴
    @QueryMapping
    suspend fun getEventItems(@Argument request: EventPageRequest): List<ItemResponse>{
        return eventReadService.getEventItems(request)
    }
}