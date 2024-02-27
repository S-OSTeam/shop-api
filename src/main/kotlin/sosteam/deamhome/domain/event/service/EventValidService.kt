package sosteam.deamhome.domain.event.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.event.entity.Event
import sosteam.deamhome.domain.event.exception.EventNotFoundException
import sosteam.deamhome.domain.event.repository.EventRepository

@Service
class EventValidService (
    private val eventRepository: EventRepository,
){
    suspend fun getEventById(id: Long): Event {
        return eventRepository.findById(id)
            ?: throw EventNotFoundException()

    }
}