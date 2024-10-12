package sosteam.deamhome.domain.cart.handler

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.cart.handler.request.CartDeleteRequest
import sosteam.deamhome.domain.cart.handler.request.CartRequest
import sosteam.deamhome.domain.cart.handler.response.CartItemResponse
import sosteam.deamhome.domain.cart.handler.response.CartResponse
import sosteam.deamhome.domain.cart.service.CartCreateService
import sosteam.deamhome.domain.cart.service.CartDeleteService
import sosteam.deamhome.domain.cart.service.CartReadService
import sosteam.deamhome.domain.cart.service.CartUpdateService
import sosteam.deamhome.global.attribute.Token
import sosteam.deamhome.global.provider.RequestProvider.Companion.getToken
import sosteam.deamhome.global.security.provider.JWTProvider

@RestController
class CartResolver(
	private val cartReadService: CartReadService,
	private val cartCreateService: CartCreateService,
	private val cartDeleteService: CartDeleteService,
	private val cartUpdateService: CartUpdateService,
	private val jwtProvider: JWTProvider,
) {
	@QueryMapping
	suspend fun getAllCartList(): List<CartResponse> {
		val accessToken = getToken()
		return cartReadService.getAllCartList(jwtProvider.getUserId(accessToken, Token.ACCESS))
	}

//    @QueryMapping
//    suspend fun calculateCartTotal(): Int{
//        return cartReadService.calculateCartTotal(authenticationService.getUserIdFromToken())
//    }
	
	@MutationMapping
	suspend fun addCartItem(@Argument request: CartRequest): CartItemResponse {
		val accessToken = getToken()
		return cartCreateService.addCartItem(jwtProvider.getUserId(accessToken, Token.ACCESS), request)
	}
	
	@MutationMapping
	// 담은 수 상관없이 무조건 지우기
	suspend fun deleteCartItem(@Argument request: CartDeleteRequest): String {
		val accessToken = getToken()
		val (itemId) = request
		return cartDeleteService.deleteCartItem(jwtProvider.getUserId(accessToken, Token.ACCESS), itemId)
	}
	
	
	@MutationMapping
	// userId, itemId 이용해서 장바구니 담은수, 체크여부 변경
	suspend fun updateCartItem(@Argument request: CartRequest): CartItemResponse {
		val accessToken = getToken()
		val (itemId, cnt, checked) = request
		return cartUpdateService.changeCartItem(jwtProvider.getUserId(accessToken, Token.ACCESS), itemId, cnt, checked)
	}
	
	
}