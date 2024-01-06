package sosteam.deamhome.domain.account.handler.request

data class WishListRequest (
    val userId: String,
    val itemId: String,
)