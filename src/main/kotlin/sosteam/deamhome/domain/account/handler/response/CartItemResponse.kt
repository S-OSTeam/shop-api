package sosteam.deamhome.domain.account.handler.response

data class CartItemResponse (
    val itemId: String,
    val cnt: Int,
    val check: Boolean,
)