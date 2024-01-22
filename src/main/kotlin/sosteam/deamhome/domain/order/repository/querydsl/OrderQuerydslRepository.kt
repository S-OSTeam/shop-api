package sosteam.deamhome.domain.order.repository.querydsl

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import sosteam.deamhome.domain.order.entity.Order

interface OrderQuerydslRepository : QuerydslR2dbcRepository<Order, Long>, ReactiveQuerydslPredicateExecutor<Order>