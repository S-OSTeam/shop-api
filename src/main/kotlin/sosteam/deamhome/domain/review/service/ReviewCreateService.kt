package sosteam.deamhome.domain.review.service

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.exception.AccountNotFoundException
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.item.exception.ItemNotFoundException
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.domain.review.entity.Review
import sosteam.deamhome.domain.review.handler.request.ReviewCreateRequest
import sosteam.deamhome.domain.review.handler.response.ReviewResponse
import sosteam.deamhome.domain.review.repository.ReviewRepository

@Service
class ReviewCreateService(
	private val reviewRepository: ReviewRepository,
	private val accountRepository: AccountRepository,
	private val itemRepository: ItemRepository
) {
	suspend fun createReview(request: ReviewCreateRequest): ReviewResponse {
		val account = accountRepository.findAccountByUserId(request.userId) ?: throw AccountNotFoundException()
		val item = itemRepository.findItemById(request.itemId) ?: throw ItemNotFoundException()
		val review = Review(
			title = request.title,
			content = request.content,
			score = request.score,
			status = false,
			userId = request.userId,
			itemId = request.itemId,
			images = request.images,
			likeUsers = listOf()
		)
		
		val savedReview = reviewRepository.save(review).awaitSingle()
		return ReviewResponse.fromReview(savedReview)
	}
}