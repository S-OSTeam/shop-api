package sosteam.deamhome.domain.account.service.cart

import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.service.AccountValidService
import sosteam.deamhome.domain.account.handler.response.CartResponse
import sosteam.deamhome.domain.item.repository.ItemRepository

@Service
class CartReadService (
    private val itemRepository: ItemRepository,
    private val accountValidService: AccountValidService,

    ){
    suspend fun getAllCartList(userId: String):List<CartResponse>{
        val account = accountValidService.getAccountByUserId(userId)

        val cartResponses = mutableListOf<CartResponse>()

        for (cartItem in account.cart) {
            val item = itemRepository.findItemById(cartItem.itemId)
            // Todo : 중간에 item이 삭제됐다면... 처리 생각
            item?.let {
                val cartResponse = CartResponse.fromItem(it, cartItem.cnt, cartItem.check)
                cartResponses.add(cartResponse)
            }
        }
        return cartResponses

    }
    suspend fun calculateCartTotal(userId: String): Int { // Todo: Int로 괜찮겠죠?
        val account = accountValidService.getAccountByUserId(userId)

        var totalAmount = 0

        for (cartItem in account.cart) {
            if(cartItem.check){
                val item = itemRepository.findItemById(cartItem.itemId)
                item?.let{
                    totalAmount += it.price * cartItem.cnt
                }
            }
        }
        return totalAmount
    }
}