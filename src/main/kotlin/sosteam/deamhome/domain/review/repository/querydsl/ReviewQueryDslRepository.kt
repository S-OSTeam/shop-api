package sosteam.deamhome.domain.review.repository.querydsl

import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.review.entity.Review

@GraphQlRepository
interface ReviewQueryDslRepository : ReactiveCrudRepository<Review, String>, ReactiveQuerydslPredicateExecutor<Review>
