package sosteam.deamhome.domain.account.repository.querydsl

import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import sosteam.deamhome.domain.account.entity.Account

interface AccountQueryDslRepository : ReactiveCrudRepository<Account, String>,
	ReactiveQuerydslPredicateExecutor<Account>