package sosteam.deamhome.domain.order.repository.querydsl

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import sosteam.deamhome.domain.order.entity.Order

interface OrderQuerydslRepository : QuerydslR2dbcRepository<Order, Long>