package sosteam.deamhome.domain.cart.handler.response

import sosteam.deamhome.domain.cart.entity.CartItem

data class CartItemResponse (
    val itemId: String,
    val cnt: Int,
    val check: Boolean,
){
    companion object{
        fun fromCartItem(cartItem: CartItem): CartItemResponse{
            return CartItemResponse(
                itemId = cartItem.itemId,
                cnt = cartItem.cnt,
                check = cartItem.checked,
            )
        }
    }
}