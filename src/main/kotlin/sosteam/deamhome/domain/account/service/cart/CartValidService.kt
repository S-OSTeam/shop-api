package sosteam.deamhome.domain.account.service.cart

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.exception.CartItemExceedsStockException
import sosteam.deamhome.domain.item.exception.ItemNotFoundException
import sosteam.deamhome.domain.item.repository.ItemRepository

@Service
class CartValidService (
    private val itemRepository: ItemRepository
){
    // 재고수량 이내로 장바구니 담을 수 있게 검사
    suspend fun isCntWithinStockLimit(itemId: String, cnt: Int):Boolean {
        val item = itemRepository.findById(itemId).awaitSingleOrNull()
            ?: throw ItemNotFoundException()

        return if(cnt > item.stockCnt){
            throw CartItemExceedsStockException()
        }else {
            true
         }
    }


}