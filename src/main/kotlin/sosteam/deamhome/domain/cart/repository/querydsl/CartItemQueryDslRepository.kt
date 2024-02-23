package sosteam.deamhome.domain.cart.repository.querydsl

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import sosteam.deamhome.domain.cart.entity.CartItem

interface CartItemQueryDslRepository: QuerydslR2dbcRepository<CartItem, Long>