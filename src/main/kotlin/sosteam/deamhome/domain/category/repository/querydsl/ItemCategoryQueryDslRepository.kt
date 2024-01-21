package sosteam.deamhome.domain.category.repository.querydsl

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import sosteam.deamhome.domain.category.entity.ItemCategory

interface ItemCategoryQueryDslRepository:
    QuerydslR2dbcRepository<ItemCategory, Long>