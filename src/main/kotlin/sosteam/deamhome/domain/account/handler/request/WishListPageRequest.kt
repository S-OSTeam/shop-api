package sosteam.deamhome.domain.account.handler.request

data class WishListPageRequest (
    val userId: String,
    val page: Int =0,
    val pageSize: Int
)