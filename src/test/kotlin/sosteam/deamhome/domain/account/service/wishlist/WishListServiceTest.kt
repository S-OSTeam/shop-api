package sosteam.deamhome.domain.account.service.wishlist

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.mockk.coEvery
import io.mockk.mockk
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.account.service.AccountValidService
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.repository.ItemRepository
import java.time.LocalDateTime

class WishListServiceTest : BehaviorSpec({
	val accountRepository = mockk<AccountRepository>()
	val itemRepository = mockk<ItemRepository>()
	val accountValidService = mockk<AccountValidService>()
	
	val wishListModifyService = WishListModifyService(accountRepository, itemRepository, accountValidService)
	Given("addOrRemoveWishListItem 테스트") {
		val existingItemId = "existingItemId"
		val newItemId = "newItemId"
		
		val account = Account(
			userId = "userId",
			pwd = "password",
			sex = true,
			birtyday = LocalDateTime.now(),
			zipcode = "12345",
			address1 = "Address 1",
			address2 = "Address 1",
			address3 = "Address 1",
			address4 = "Address 1",
			email = "test@example.com",
			receiveMail = true,
			createdIp = "127.0.0.1",
			phone = "123456789",
			userName = "user123",
			loginAt = LocalDateTime.now()
		)
		val item = Item(
			publicId = 1L,
			title = "test",
			categoryPublicId = 1L,
			sellerId = "test",
			content = "",
			summary = ""
		).apply { images = mutableListOf() }

		account.addWishListItem(existingItemId)
		coEvery { accountRepository.save(account) } returns Mono.just(account)
		coEvery { accountValidService.getAccountByUserId("userId")} returns account
		coEvery { itemRepository.save(item) } returns Mono.just(item)
		
		When("이미 존재하는 itemId 라면") {
			coEvery { itemRepository.findItemById(existingItemId) } returns item

			val result = wishListModifyService.addOrRemoveWishListItem(account.userId, existingItemId)
			Then("삭제한다") {
				result shouldNotContain existingItemId
			}
		}
		When("존재하지 않는 itemId 라면") {
			coEvery { itemRepository.findItemById(newItemId) } returns item

			val result = wishListModifyService.addOrRemoveWishListItem(account.userId, newItemId)
			Then("추가한다") {
				result shouldContain newItemId
			}
		}
	}
	
})