package sosteam.deamhome.domain.account.service.cart

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.exception.CartItemNotFoundException
import sosteam.deamhome.domain.account.handler.response.CartItemResponse
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.account.service.AccountValidService

@Service
class CartDeleteService (
    private val accountRepository: AccountRepository,
    private val accountValidService: AccountValidService,
){
    suspend fun deleteCartItem(userId: String, itemId: String):List<CartItemResponse>{
        val account = accountValidService.getAccountByUserId(userId)

        val removedCartItem = account.cart.find { it.itemId == itemId }

        // 삭제하고자 하는 아이템 없음
        if (removedCartItem == null) {
            throw CartItemNotFoundException()
        }

        account.cart.remove(removedCartItem)
        return accountRepository.save(account).cart.map {
            CartItemResponse(it.itemId, it.cnt, it.check)
        }
    }
}
