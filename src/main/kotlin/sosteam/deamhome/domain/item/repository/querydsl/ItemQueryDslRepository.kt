package sosteam.deamhome.domain.item.repository.querydsl

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import sosteam.deamhome.domain.item.entity.Item

interface ItemQueryDslRepository: QuerydslR2dbcRepository<Item, Long>