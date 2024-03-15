package sosteam.deamhome.domain.event.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.event.entity.Event
import sosteam.deamhome.domain.event.entity.enum.EventType
import java.time.LocalDateTime

@GraphQlRepository
interface EventRepository: CoroutineCrudRepository<Event, Long> {
    suspend fun findByEndedAtAfterAndEventType(
        @Param("endDate") endDate: LocalDateTime,
        @Param("eventType") eventType: EventType): List<Event>

}