package sosteam.deamhome.domain.item.repository.querydsl

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import sosteam.deamhome.domain.item.entity.ItemCategory

interface ItemCategoryQueryDslRepository : QuerydslR2dbcRepository<ItemCategory, Long>