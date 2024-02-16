package sosteam.deamhome.domain.cart.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.account.service.AccountValidService
import sosteam.deamhome.domain.cart.exception.CartCheckListInvalidException
import sosteam.deamhome.domain.cart.exception.CartItemNotFoundException
import sosteam.deamhome.domain.cart.handler.response.CartItemResponse
import sosteam.deamhome.domain.cart.repository.CartRepository

@Service
class CartUpdateService (
    private val accountRepository: AccountRepository,
    private val cartRepository: CartRepository,
    private val cartValidService: CartValidService,
){

    // userId, itemId에 해당하는 장바구니 아이템 개수, 체크여부 변경 함수
    suspend fun changeCartItem(userId: String,itemId:String, cnt:Int, checked: Boolean): CartItemResponse {

        val changedItem = cartRepository.findByUserIdAndItemId(userId, itemId)

        if(changedItem == null){
            throw CartItemNotFoundException()
        }

        if(cartValidService.isCntWithinStockLimit(itemId, cnt)){ // 재고수량 안의 변경임
            changedItem.checked = checked
            changedItem.cnt = cnt
            cartRepository.save(changedItem)
            return CartItemResponse.fromCartItem(changedItem)
        }else{
            throw CartCheckListInvalidException()
        }
    }
//
//    suspend fun updateCartCheckStatus(userId: String, checkList: List<Boolean>):List<CartItemResponse>{
//        val account = accountValidService.getAccountByUserId(userId)
//
//        if (account.cart.size != checkList.size){
//            throw CartCheckListInvalidException()
//        }
//
//        account.cart.forEachIndexed { index, cartItem ->
//            cartItem.check = checkList[index]
//        }
//        accountRepository.save(account)
//
//        return account.cart.map { CartItemResponse(it.itemId, it.cnt, it.check) }
//    }

}