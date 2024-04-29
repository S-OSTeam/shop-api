package sosteam.deamhome.domain.cart.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitSingleOrNull
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.graphql.data.GraphQlRepository
import org.springframework.r2dbc.core.flow
import sosteam.deamhome.domain.cart.entity.CartItem
import sosteam.deamhome.domain.cart.entity.QCartItem
import sosteam.deamhome.domain.cart.repository.CartRepository
import sosteam.deamhome.domain.cart.repository.custom.CartItemRepositoryCustom
import sosteam.deamhome.domain.cart.repository.querydsl.CartItemQueryDslRepository
import sosteam.deamhome.domain.item.entity.QItem

@GraphQlRepository
class CartItemRepositoryImpl (
    private val repository: CartItemQueryDslRepository
) : CartItemRepositoryCustom{

    private val item = QItem.item
    private val cartItem = QCartItem.cartItem


//    override suspend fun findByItemIdAndUserId(itemId: String, userId: String): CartItem?{
//        return repository.findBy
//    }
}