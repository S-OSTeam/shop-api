package sosteam.deamhome.domain.account.resolver

import sosteam.deamhome.domain.account.handler.request.CartCheckListRequest
import sosteam.deamhome.domain.account.handler.request.CartDeleteRequest
import sosteam.deamhome.domain.account.handler.request.CartRequest
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.account.handler.response.CartItemResponse
import sosteam.deamhome.domain.account.service.cart.CartCreateService
import sosteam.deamhome.domain.account.service.cart.CartReadService
import sosteam.deamhome.domain.account.handler.response.CartResponse
import sosteam.deamhome.domain.account.service.cart.CartDeleteService
import sosteam.deamhome.domain.account.service.cart.CartUpdateService
import sosteam.deamhome.global.security.service.AuthenticationService

@RestController
class CartResolver (
    private val cartReadService: CartReadService,
    private val cartCreateService: CartCreateService,
    private val cartDeleteService: CartDeleteService,
    private val cartUpdateService: CartUpdateService,
    private val authenticationService: AuthenticationService,
){
    @QueryMapping
    suspend fun getAllCartList(): List<CartResponse>{
        return cartReadService.getAllCartList(authenticationService.getUserIdFromToken())
    }

    @QueryMapping
    suspend fun calculateCartTotal(): Int{
        return cartReadService.calculateCartTotal(authenticationService.getUserIdFromToken())
    }

    @MutationMapping
    suspend fun addCartItem(@Argument request: sosteam.deamhome.domain.account.handler.request.CartRequest): List<CartItemResponse>{
        val (itemId, cnt) = request
        return cartCreateService.addCartItem(authenticationService.getUserIdFromToken(), itemId, cnt)
    }

    @MutationMapping
    // 담은 수 상관없이 무조건 지우기
    suspend fun deleteCartItem(@Argument request: sosteam.deamhome.domain.account.handler.request.CartDeleteRequest):List<CartItemResponse>{
        val (itemId) = request
        return cartDeleteService.deleteCartItem(authenticationService.getUserIdFromToken(), itemId)
    }

    @MutationMapping
    suspend fun updateCartCheckStatus(@Argument request: sosteam.deamhome.domain.account.handler.request.CartCheckListRequest):List<CartItemResponse>{
        val (checkList) = request
        return cartUpdateService.updateCartCheckStatus(authenticationService.getUserIdFromToken(), checkList)
    }

    @MutationMapping
    suspend fun updateCartItemCnt(@Argument request: sosteam.deamhome.domain.account.handler.request.CartRequest): List<CartItemResponse>{
        val (itemId, cnt) = request
        return cartUpdateService.changeCartItemCnt(authenticationService.getUserIdFromToken(), itemId, cnt)
    }


}