package sosteam.deamhome.domain.account.service.wishlist

import kotlinx.coroutines.flow.toList
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.service.AccountValidService
import sosteam.deamhome.domain.item.handler.response.ItemResponse
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.domain.item.service.ItemSearchService

@Service
class WishListReadService(
	private val accountValidService: AccountValidService,
	private val itemSearchService: ItemSearchService,
) {
	suspend fun getAllWishList(userId: String, page: Int, pageSize: Int = 10): List<ItemResponse> {
		val account = accountValidService.getAccountByUserId(userId)
		return itemSearchService
			.findItemByPublicIdIn(account.getWishList(), page, pageSize)
	}
}