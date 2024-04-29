package sosteam.deamhome.domain.cart.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.cart.handler.response.CartItemResponse
import sosteam.deamhome.domain.cart.entity.CartItem
import sosteam.deamhome.domain.cart.exception.CartCheckListInvalidException
import sosteam.deamhome.domain.cart.exception.CartItemExceedsStockException
import sosteam.deamhome.domain.cart.handler.request.CartRequest
import sosteam.deamhome.domain.cart.repository.CartRepository

@Service
class CartCreateService (
    private val cartValidService: CartValidService,
    private val cartRepository: CartRepository,
){
    //새로운 item의 개수를 cnt로 카트에 담기
    suspend fun addCartItem(userId: String, request: CartRequest): CartItemResponse{
        val (itemId, cnt) = request

        val existingCartItem = cartRepository.findByUserIdAndItemId(userId, itemId)
        if(existingCartItem != null){ //추가하려는 아이템이 이미 존재
            //Todo: updateService에서 이미 있는 아이템 개수 추가 넣기
            throw CartCheckListInvalidException()
        }

        if(cartValidService.isCntWithinStockLimit(itemId,cnt)){

            val cartItem: CartItem = CartItem(
                id = null,
                itemId = itemId,
                userId = userId,
                cnt = cnt,
                checked = true
            )

            cartRepository.save(cartItem)
            return CartItemResponse.fromCartItem(cartItem)
        }else{
            throw CartCheckListInvalidException()
        }
    }

}