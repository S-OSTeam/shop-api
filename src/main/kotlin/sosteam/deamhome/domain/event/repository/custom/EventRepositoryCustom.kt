package sosteam.deamhome.domain.event.repository.custom

import kotlinx.coroutines.flow.Flow
import sosteam.deamhome.domain.event.entity.Event
import sosteam.deamhome.domain.event.handler.request.EventFilter

interface EventRepositoryCustom {
    fun findEvent(filter: EventFilter?): Flow<Event>
}