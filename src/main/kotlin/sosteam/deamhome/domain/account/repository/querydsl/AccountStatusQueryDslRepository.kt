package sosteam.deamhome.domain.account.repository.querydsl

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import sosteam.deamhome.domain.account.entity.AccountStatus

interface AccountStatusQueryDslRepository : QuerydslR2dbcRepository<AccountStatus, Long>