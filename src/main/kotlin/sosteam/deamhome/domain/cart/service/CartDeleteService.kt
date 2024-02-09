package sosteam.deamhome.domain.cart.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.account.service.AccountValidService
import sosteam.deamhome.domain.cart.entity.CartItem
import sosteam.deamhome.domain.cart.exception.CartItemNotFoundException
import sosteam.deamhome.domain.cart.handler.response.CartItemResponse
import sosteam.deamhome.domain.cart.repository.CartRepository

@Service
class CartDeleteService (
    private val accountValidService: AccountValidService,
    private val cartRepository: CartRepository,
){
    suspend fun deleteCartItem(userId: String, itemId: String): String{
        val account = accountValidService.getAccountByUserId(userId)

        val removedCartItem = cartRepository.findByUserIdAndItemId(userId, itemId)

        // 삭제하고자 하는 아이템 없음
        if (removedCartItem == null) {
            throw CartItemNotFoundException()
        }

        cartRepository.delete(removedCartItem)
        return itemId
    }
}
