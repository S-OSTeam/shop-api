package sosteam.deamhome.domain.cart.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.cart.entity.CartItem
import sosteam.deamhome.domain.cart.repository.custom.CartItemRepositoryCustom
import sosteam.deamhome.domain.item.entity.Item

@GraphQlRepository
interface CartRepository : CoroutineCrudRepository<CartItem, Long>, CartItemRepositoryCustom{
    fun findByUserId(userId: String): Flow<CartItem>
    suspend fun findByUserIdAndItemId(userId: String, itemId: String): CartItem?
}
