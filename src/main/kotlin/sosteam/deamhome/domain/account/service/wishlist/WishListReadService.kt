package sosteam.deamhome.domain.account.service.wishlist

import kotlinx.coroutines.flow.toList
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.service.AccountValidService
import sosteam.deamhome.domain.item.handler.response.ItemResponse
import sosteam.deamhome.domain.item.repository.ItemRepository

@Service
class WishListReadService (
    private val itemRepository: ItemRepository,
    private val accountValidService: AccountValidService,
){
    suspend fun getAllWishList(userId: String, page:Int = 10, pageSize:Int):List<ItemResponse>{
        val account = accountValidService.getAccountByUserId(userId)
        val pageRequest: PageRequest = PageRequest.of(page, pageSize, Sort.by(Sort.Order.desc("id")))
        return itemRepository.findByIdIn(account.getWishlist(),pageRequest)
            .toList()
            .map { ItemResponse.fromItem(it) }
    }

}