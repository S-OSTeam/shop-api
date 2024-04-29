package sosteam.deamhome.domain.cart.service

import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.service.AccountValidService
import sosteam.deamhome.domain.cart.handler.response.CartResponse
import sosteam.deamhome.domain.cart.repository.CartRepository
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.repository.ItemRepository

@Service
class CartReadService (
    private val itemRepository: ItemRepository,
    private val cartRepository: CartRepository,
    ){

    suspend fun getAllCartList(userId: String):List<CartResponse>{

        val cartResponses = mutableListOf<CartResponse>()
        val cartItems= cartRepository.findByUserId(userId).toList()
        for (cartItem in cartItems) {
            val item: Item? = itemRepository.findByPublicId(cartItem.itemId)
            val cartResponse = CartResponse.fromItem(item, cartItem.cnt, cartItem.checked)
            cartResponses.add(cartResponse)
        }
        return cartResponses
    }
//    suspend fun calculateCartTotal(userId: String): Int { // Todo: Int로 괜찮겠죠?
//        val account = accountValidService.getAccountByUserId(userId)
//
//        var totalAmount = 0
//
//        for (cartItem in account.cart) {
//            if(cartItem.check){
//                val item = itemRepository.findByPublicId(cartItem.itemId)
//                item?.let{
//                    totalAmount += it.price * cartItem.cnt
//                }
//            }
//        }
//        return totalAmount
//    }
}