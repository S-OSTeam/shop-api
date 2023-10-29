package sosteam.deamhome.domain.account.repository.querydsl

import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor
import sosteam.deamhome.domain.account.entity.Account

interface AccountQueryDslRepository :
	ReactiveQuerydslPredicateExecutor<Account>