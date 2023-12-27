package sosteam.deamhome.domain.account.service.wishlist

import kotlinx.coroutines.flow.toList
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.account.service.AccountValidService
import sosteam.deamhome.domain.item.entity.dto.response.ItemResponseDTO
import sosteam.deamhome.domain.item.repository.ItemRepository

@Service
class WishListReadService (
    private val itemRepository: ItemRepository,
    private val accountValidService: AccountValidService,
){
    suspend fun getAllWishList(userId: String, page:Int):List<ItemResponseDTO>{
        //Todo: wishList에서 itemId 찾아서 리스트로 가져오기
        val pageSize = 10
        val account = accountValidService.getAccountByUserId(userId)
        val pageRequest: PageRequest = PageRequest.of(page, pageSize, Sort.by(Sort.Order.desc("id")))
        return itemRepository.findByIdIn(account.getWishlist(),pageRequest)
            .toList()
            .map { ItemResponseDTO.fromItem(it) }

    }

}