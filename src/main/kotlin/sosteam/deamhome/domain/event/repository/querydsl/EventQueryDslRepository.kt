package sosteam.deamhome.domain.event.repository.querydsl;

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository;
import sosteam.deamhome.domain.event.entity.Event;

interface EventQueryDslRepository : QuerydslR2dbcRepository<Event, Long>
