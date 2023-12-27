package sosteam.deamhome.domain.account.resolver

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.account.handler.request.WishListRequestDTO
import sosteam.deamhome.domain.account.service.wishlist.WishListModifyService
import sosteam.deamhome.domain.account.service.wishlist.WishListReadService
import sosteam.deamhome.domain.item.entity.dto.response.ItemResponseDTO
@RestController
class WishListResolver (
    private val wishListModifyService: WishListModifyService,
    private val wishListReadService: WishListReadService,
){
    @QueryMapping
    suspend fun getWishList(@Argument userId:String, @Argument page:Int): List<ItemResponseDTO>{
        return wishListReadService.getAllWishList(userId, page)
    }
    @MutationMapping
    suspend fun updateWishListItemInclude(@Argument request: WishListRequestDTO): List<String>{
        val (userId, itemId) = request
        return wishListModifyService.addOrRemoveWishListItem(userId, itemId)
    }
}