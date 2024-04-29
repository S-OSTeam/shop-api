package sosteam.deamhome.domain.event.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.event.repository.EventRepository

@Service
class EventDeleteService(
    private val eventRepository: EventRepository,
    private val eventValidService: EventValidService,
) {
    suspend fun deleteEvent(eventId:Long){
        val event = eventValidService.getEventById(eventId)
        eventRepository.deleteById(eventId)
    }

}