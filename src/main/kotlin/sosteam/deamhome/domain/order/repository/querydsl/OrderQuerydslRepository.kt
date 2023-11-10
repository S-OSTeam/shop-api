package sosteam.deamhome.domain.order.repository.querydsl

import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import sosteam.deamhome.domain.order.entity.Order

interface OrderQuerydslRepository : ReactiveCrudRepository<Order, String>, ReactiveQuerydslPredicateExecutor<Order>