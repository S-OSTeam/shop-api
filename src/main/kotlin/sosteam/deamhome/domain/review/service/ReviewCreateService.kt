package sosteam.deamhome.domain.review.service

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.account.exception.AccountNotFoundException
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.item.exception.ItemNotFoundException
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.domain.review.entity.Review
import sosteam.deamhome.domain.review.handler.request.ReviewCreateRequest
import sosteam.deamhome.domain.review.handler.response.ReviewResponse
import sosteam.deamhome.domain.review.repository.ReviewRepository
import sosteam.deamhome.global.image.provider.ImageProvider

@Service
@Transactional
class ReviewCreateService(
	private val reviewRepository: ReviewRepository,
	private val accountRepository: AccountRepository,
	private val itemRepository: ItemRepository,
	private val imageProvider: ImageProvider
) {
	suspend fun createReview(request: ReviewCreateRequest): ReviewResponse {
		val review = Review(
			title = request.title,
			content = request.content,
			like = 0,
			score = request.score,
			status = false,
			account = accountRepository.findAccountByUserId(request.userId) ?: throw AccountNotFoundException(),
			item = itemRepository.findItemById(request.itemId) ?: throw ItemNotFoundException(),
			images = ArrayList(request.images.map {
				imageProvider.saveImage(it, "review", "").awaitSingle() // TODO: inner 꼭 필요한지 확인
			}),
			likeUsers = listOf()
		)
		
		reviewRepository.save(review).awaitSingle()
		return ReviewResponse.fromReview(review)
	}
}