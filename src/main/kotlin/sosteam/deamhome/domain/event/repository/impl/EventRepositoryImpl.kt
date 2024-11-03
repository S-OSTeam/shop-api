package sosteam.deamhome.domain.event.repository.impl

import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import kotlinx.coroutines.flow.Flow
import org.springframework.graphql.data.GraphQlRepository
import org.springframework.r2dbc.core.flow
import sosteam.deamhome.domain.event.entity.Event
import sosteam.deamhome.domain.event.handler.request.EventFilter
import sosteam.deamhome.domain.event.repository.custom.EventRepositoryCustom
import sosteam.deamhome.domain.event.repository.querydsl.EventQueryDslRepository
import sosteam.deamhome.domain.event.entity.QEvent

@GraphQlRepository
class EventRepositoryImpl (
    private val repository: EventQueryDslRepository
): EventRepositoryCustom{
    private val event = QEvent.event

    override fun findEvent(filter: EventFilter?): Flow<Event> {
        return repository.query{ query ->
            query
                .select(repository.entityProjection())
                .from(event)
                .where(
                    listOfNotNull(
                        containsTitle(filter?.title),
                        filter?.startedAt?.let{ event.startedAt.goe(it)},
                        filter?.endedAt?.let{event.endedAt.loe(it)},
                        filter?.eventType?.let{event.eventType.eq(it)},
                        filter?.id?.let{event.id.eq(it)},
                    ).reduceOrNull{acc, expression -> acc.and(expression)}
                )
//                .orderBy(event.startedAt)
                .orderBy(OrderSpecifier(Order.DESC, event.startedAt))
                // 페이지네이션 미포
        }.flow()
    }
    private fun containsTitle(title: String?): BooleanExpression? {
        return title?.let { event.title.contains(it) }
    }
}