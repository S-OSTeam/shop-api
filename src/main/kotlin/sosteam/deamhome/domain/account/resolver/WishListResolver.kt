package sosteam.deamhome.domain.account.resolver

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.account.service.wishlist.WishListService
import sosteam.deamhome.domain.item.entity.dto.response.ItemResponseDTO

@RestController
class WishListResolver (
    private val wishListService: WishListService
){
    @QueryMapping
    suspend fun getWishList(@Argument userId:String, @Argument page:Int): List<ItemResponseDTO>{
        return wishListService.getAllWishList(userId, page)
    }
    @MutationMapping
    suspend fun updateWishListItemInclude(@Argument userId: String,@Argument itemId: String ): List<String>{
        println("updateWishListItemInclude")
        return wishListService.addOrRemoveWishListItem(userId, itemId)
    }
}