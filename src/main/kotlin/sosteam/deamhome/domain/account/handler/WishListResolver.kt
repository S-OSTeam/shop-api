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
import sosteam.deamhome.global.attribute.Token
import sosteam.deamhome.global.provider.RequestProvider.Companion.getToken
import sosteam.deamhome.global.security.provider.JWTProvider
import sosteam.deamhome.global.security.service.AuthenticationService

@RestController
class WishListResolver(
	private val wishListModifyService: WishListModifyService,
	private val wishListReadService: WishListReadService,
	private val authenticationService: AuthenticationService,
	private val jwtProvider: JWTProvider
) {
	@QueryMapping
	suspend fun getWishList(@Argument request: WishListPageRequest): List<ItemResponse> {
		val token = getToken()
		val (page, pageSize) = request
		return wishListReadService.getAllWishList(jwtProvider.getUserId(token, Token.ACCESS), page, pageSize)
	}
	
	@MutationMapping
	suspend fun updateWishListItemInclude(@Argument request: WishListRequest): List<String> {
		val token = getToken()
		val (itemId) = request
		return wishListModifyService.addOrRemoveWishListItem(jwtProvider.getUserId(token, Token.ACCESS), itemId)
	}
}