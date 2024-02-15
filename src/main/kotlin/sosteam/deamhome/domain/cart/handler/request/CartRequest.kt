package sosteam.deamhome.domain.cart.handler.request

import sosteam.deamhome.domain.cart.entity.CartItem
import sosteam.deamhome.global.entity.DTO

data class CartRequest (
    val itemId: String,
    val cnt: Int,
    val checked: Boolean,
)
