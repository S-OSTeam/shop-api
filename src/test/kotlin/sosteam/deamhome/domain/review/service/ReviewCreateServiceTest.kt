package sosteam.deamhome.domain.review.service

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.domain.review.entity.Review
import sosteam.deamhome.domain.review.handler.request.ReviewCreateRequest
import sosteam.deamhome.domain.review.repository.ReviewRepository

class ReviewCreateServiceTest : BehaviorSpec({
	isolationMode = IsolationMode.InstancePerLeaf
	val reviewRepository = mockk<ReviewRepository>()
	val accountRepository = mockk<AccountRepository>()
	val itemRepository = mockk<ItemRepository>()
	val reviewCreateService = ReviewCreateService(reviewRepository, accountRepository, itemRepository)
	
	Given("리뷰의 request가 주어지고") {
		val request = ReviewCreateRequest(
			title = "Title",
			content = "test content",
			score = 4.5,
			status = false,
			userId = "user123",
			itemId = "item123",
			images = listOf("1234", "5678")
		)
		val mockReview = Review(
			title = "Title",
			content = "test content",
			score = 4.5,
			status = false,
			userId = "user123",
			itemId = "item123",
			images = listOf("1234", "5678"),
			likeUsers = listOf()
		)
		val mockAccount = mockk<Account>()
		val mockItem = mockk<Item>()
		
		coEvery { reviewRepository.save(any()) } answers { Mono.just(mockReview) }
		coEvery { accountRepository.findAccountByUserId(any()) } answers { mockAccount }
		coEvery { itemRepository.findItemById(any()) } answers { mockItem }
		
		When("저장할 때") {
			val result = reviewCreateService.createReview(request)
			Then("잘 저장된다") {
				result.title shouldBe "Title"
			}
		}
	}
})