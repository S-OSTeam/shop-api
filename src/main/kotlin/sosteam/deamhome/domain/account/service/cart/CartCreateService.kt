package sosteam.deamhome.domain.account.service.cart

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.entity.CartItem
import sosteam.deamhome.domain.account.handler.response.CartItemResponse
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.account.service.AccountValidService

@Service
class CartCreateService (
    private val accountValidService: AccountValidService,
    private val cartValidService: CartValidService,
    private val accountRepository: AccountRepository,
){
    //새로운 item을 개수만큼 카트에 담기
    suspend fun addCartItem(userId: String, itemId: String, cnt: Int): List<CartItemResponse>{
        val account = accountValidService.getAccountByUserId(userId)
        val existingCartItem = account.cart.find { it.itemId == itemId }

        if(existingCartItem != null){ //추가하려는 아이템이 이미 존재
            //Todo: updateService에서 이미 있는 아이템 갯수 추가 넣기
        }else{
            if(cartValidService.isCntWithinStockLimit(itemId,cnt)){
                account.cart.add(CartItem(itemId, cnt, true))
            }
        }
        val updatedAccount = accountRepository.save(account)

        return updatedAccount.cart.map {
            CartItemResponse(it.itemId, it.cnt, it.check)
        }
    }

}