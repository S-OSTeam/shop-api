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
import sosteam.deamhome.domain.cart.service.CartReadService
import sosteam.deamhome.domain.cart.service.CartDeleteService
import sosteam.deamhome.domain.cart.service.CartUpdateService
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

//    @QueryMapping
//    suspend fun calculateCartTotal(): Int{
//        return cartReadService.calculateCartTotal(authenticationService.getUserIdFromToken())
//    }

    @MutationMapping
    suspend fun addCartItem(@Argument request: CartRequest): CartItemResponse{
        return cartCreateService.addCartItem(authenticationService.getUserIdFromToken(),request)
    }

    @MutationMapping
    // 담은 수 상관없이 무조건 지우기
    suspend fun deleteCartItem(@Argument request: CartDeleteRequest): String{
        val (itemId) = request
        return cartDeleteService.deleteCartItem(authenticationService.getUserIdFromToken(), itemId)
    }

//    @MutationMappinggit s
//    suspend fun updateCartCheckStatus(@Argument request: CartCheckListRequest):List<CartItemResponse>{
//        val (checkList) = request
//        return cartUpdateService.updateCartCheckStatus(authenticationService.getUserIdFromToken(), checkList)
//    }
//
//    @MutationMapping
//    suspend fun updateCartItemCnt(@Argument request: CartRequest): List<CartItemResponse>{
//        val (itemId, cnt) = request
//        return cartUpdateService.changeCartItemCnt(authenticationService.getUserIdFromToken(), itemId, cnt)
//    }


}