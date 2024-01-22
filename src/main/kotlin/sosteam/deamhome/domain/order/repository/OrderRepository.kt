package sosteam.deamhome.domain.order.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.order.entity.Order
import sosteam.deamhome.domain.order.repository.custom.OrderRepositoryCustom

@GraphQlRepository
interface OrderRepository : CoroutineCrudRepository<Order, Long>, OrderRepositoryCustom