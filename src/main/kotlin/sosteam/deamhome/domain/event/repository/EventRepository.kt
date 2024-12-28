package sosteam.deamhome.domain.event.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.event.entity.Event
import sosteam.deamhome.domain.event.entity.EventType
import sosteam.deamhome.domain.event.repository.custom.EventRepositoryCustom
import java.time.LocalDateTime
import java.time.OffsetDateTime

@GraphQlRepository
interface EventRepository : CoroutineCrudRepository<Event, Long> , EventRepositoryCustom{


	suspend fun findByEndedAtAfterAndEventType(
		@Param("ended_at") endDate: OffsetDateTime?,
		@Param("eventType") eventType: EventType?
	):List<Event>
}

