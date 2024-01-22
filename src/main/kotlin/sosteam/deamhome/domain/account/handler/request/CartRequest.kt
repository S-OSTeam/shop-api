package sosteam.deamhome.domain.account.handler.request

data class CartRequest (
    val itemId: String,
    val cnt: Int,
)