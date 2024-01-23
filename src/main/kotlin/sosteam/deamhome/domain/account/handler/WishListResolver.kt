package sosteam.deamhome.domain.account.handler

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.account.handler.request.WishListPageRequest
import sosteam.deamhome.domain.account.handler.request.WishListRequest
import sosteam.deamhome.domain.account.service.wishlist.WishListModifyService
import sosteam.deamhome.domain.account.service.wishlist.WishListReadService
import sosteam.deamhome.domain.item.handler.response.ItemResponse

@RestController
class WishListResolver (
    private val wishListModifyService: WishListModifyService,
    private val wishListReadService: WishListReadService,
){
    @QueryMapping
    suspend fun getWishList(@Argument request: WishListPageRequest): List<ItemResponse>{
        val (userId, page, pageSize) = request
        return wishListReadService.getAllWishList(userId, page, pageSize)
    }
    @MutationMapping
    suspend fun updateWishListItemInclude(@Argument request: WishListRequest): List<String>{
        val (userId, itemId) = request
        return wishListModifyService.addOrRemoveWishListItem(userId, itemId)
    }
}