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
		account.addWishListItem(existingItemId)
		coEvery { accountRepository.save(account) } returns Mono.just(account)
		
		When("이미 존재하는 itemId 라면") {
			val result = wishListModifyService.addOrRemoveWishListItem(account.id, existingItemId)
			Then("삭제한다") {
				result shouldNotContain existingItemId
			}
		}
		When("존재하지 않는 itemId라면") {
			val result = wishListModifyService.addOrRemoveWishListItem(account.id, newItemId)
			Then("추가한다") {
				result shouldContain newItemId
			}
		}
	}

//    Given("getAllWishList 테스트"){
//        val itemId1 = "item1"
//        val itemId2 = "item2"
//        val itemId3 = "item3"
//
//        val account = Account(
//            userId = "userId",
//            pwd = "password",
//            sex = true,
//            birtyday = LocalDateTime.now(),
//            zipcode = "12345",
//            address1 = "Address 1",
//            address2 = "Address 1",
//            address3 = "Address 1",
//            address4 = "Address 1",
//            email = "test@example.com",
//            receiveMail = true,
//            createdIp = "127.0.0.1",
//            phone = "123456789",
//            userName = "user123",
//            loginAt = LocalDateTime.now()
//        )
//        account.addWishListItem(itemId1)
//        account.addWishListItem(itemId2)
//        account.addWishListItem(itemId3)
//
//        val item1 = Item("item1","content","summary",0,0,0,0,0.1,0,0,false,"123")
//        val item2 = Item("item1","content","summary",0,0,0,0,0.1,0,0,false,"123")
//        val item3 = Item("item1","content","summary",0,0,0,0,0.1,0,0,false,"123")
//
//        coEvery { accountRepository.save(account) } returns Mono.just(account)
//        coEvery { itemRepository.findByIdIn(any()) } returns listOf(item1, item2,item3)
//        When("위시리스트에 아이템 ID 들어있다면"){
//            val result = wishListService.getAllWishList(account)
//            Then("아이템 리스트 반환한다"){
//                result shouldBe listOf<Item>(
//                    item1,
//                    item2,
//                    item3
//                )
//            }
//        }
//    }
//

})