package sosteam.deamhome.domain.category.repository.querydsl

import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import sosteam.deamhome.domain.category.entity.ItemCategory

interface ItemCategoryQueryDslRepository: ReactiveCrudRepository<ItemCategory, String>,
    ReactiveQuerydslPredicateExecutor<ItemCategory>