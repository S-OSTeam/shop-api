package sosteam.deamhome.domain.account.repository.querydsl

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor
import sosteam.deamhome.domain.account.entity.Account

interface AccountQueryDslRepository : QuerydslR2dbcRepository<Account, Long>