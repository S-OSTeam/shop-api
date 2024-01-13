package sosteam.deamhome.domain.account.service.cart

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.entity.CartItem
import sosteam.deamhome.domain.account.exception.CartCheckListInvalidException
import sosteam.deamhome.domain.account.exception.CartItemNotFoundException
import sosteam.deamhome.domain.account.handler.response.CartItemResponse
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.account.service.AccountValidService

@Service
class CartUpdateService (
    private val accountRepository: AccountRepository,
    private val accountValidService: AccountValidService,
    private val cartValidService: CartValidService,
){

    // itemId에 해당하는 아이템 개수를 cnt로 바꾼다
    suspend fun changeCartItemCnt(userId: String,itemId:String, cnt:Int): List<CartItemResponse>{
        val account = accountValidService.getAccountByUserId(userId)

        val cartItem = account.cart.find { it.itemId == itemId }

        if(cartItem != null){

            // 장바구니는 음수이거나 재고수량 초과면 안됨
            if(cartValidService.isCntWithinStockLimit(cartItem.itemId,cnt)){
                account.cart[account.cart.indexOf(cartItem)].cnt = cnt
                accountRepository.save(account).awaitSingle()
            }
            return account.cart.map {
                CartItemResponse(it.itemId, it.cnt, it.check)
            }
        }else{ // itemId에 해당하는 아이템 존재하지 않음
            throw CartItemNotFoundException()
        }
    }

    suspend fun updateCartCheckStatus(userId: String, checkList: List<Boolean>):List<CartItemResponse>{
        val account = accountValidService.getAccountByUserId(userId)

        if (account.cart.size != checkList.size){
            throw CartCheckListInvalidException()
        }

        account.cart.forEachIndexed { index, cartItem ->
            cartItem.check = checkList[index]
        }
        accountRepository.save(account).awaitSingle()

        return account.cart.map { CartItemResponse(it.itemId, it.cnt, it.check) }
    }

}