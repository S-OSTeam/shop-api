package sosteam.deamhome.domain.account.service.wishlist

import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.exception.WishListOverflowException
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.account.service.AccountValidService
import sosteam.deamhome.domain.item.exception.ItemNotFoundException
import sosteam.deamhome.domain.item.repository.ItemRepository

@Service
class WishListModifyService(
	private val accountRepository: AccountRepository,
	private val itemRepository: ItemRepository,
	private val accountValidService: AccountValidService,
) {
	suspend fun addOrRemoveWishListItem(userId: String, itemId: String): List<String> {
		val account = accountValidService.getAccountByUserId(userId)
		if (account.isItemIdInWishlist(itemId)) { //이미 존재함
			deleteItem(account, itemId)
		} else {
			addItem(account, itemId)
		}
		return account.getWishList()
	}
	
	suspend fun addItem(account: Account, itemId: String): Account {
		
		if (account.getWishListSize() > Account.maxWishListSize) {
			throw WishListOverflowException()
		} else {
			account.addWishListItem(itemId)
			accountRepository.save(account).awaitSingle()
			
			val item = itemRepository.findItemById(itemId)?.let {
				it.wishCnt++
				itemRepository.save(it).awaitSingle()
			} ?: throw ItemNotFoundException()
			
		}
		return account
	}
	
	suspend fun deleteItem(account: Account, itemId: String): Account {
		account.removeWishListItem(itemId)
		val item = itemRepository.findItemById(itemId)?.let {
			it.wishCnt--
			itemRepository.save(it).awaitSingle()
		} ?: throw ItemNotFoundException()
		return accountRepository.save(account).awaitSingle()
	}
}