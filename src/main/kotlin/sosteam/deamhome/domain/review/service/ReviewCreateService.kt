package sosteam.deamhome.domain.review.service

import com.github.f4b6a3.ulid.UlidCreator
import org.springframework.stereotype.Service
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
class ReviewCreateService(
	private val reviewRepository: ReviewRepository,
	private val accountRepository: AccountRepository,
	private val itemRepository: ItemRepository,
	private val imageProvider: ImageProvider,
) {
	
	suspend fun createReview(request: ReviewCreateRequest): ReviewResponse {
		val account = accountRepository.findAccountByUserId(request.userId) ?: throw AccountNotFoundException()
		val item = itemRepository.findByPublicId(request.itemId) ?: throw ItemNotFoundException()
		item.avgReview = (item.avgReview * item.reviewCnt + request.score) / item.reviewCnt
		item.reviewCnt++
		itemRepository.save(item)
		
		val publicId = UlidCreator.getMonotonicUlid().toString().replace("-", "")
		val parentPublicId = if (request.parentPublicId.isNullOrEmpty()) publicId else request.parentPublicId
		
		val imageUrls = if (request.images.isNullOrEmpty()) {
			mutableListOf()
		} else {
			request.images.toMutableList()
		}
		
		val laterReview = Review(
			id = null,
			publicId = publicId,
			parentPublicId = parentPublicId,
			title = request.title,
			content = request.content,
			score = request.score,
			status = false,
			userId = request.userId,
			itemId = request.itemId,
			imageUrls = imageUrls,
			likeUsers = mutableListOf(),
			purchaseOptions = if (request.purchaseOptions.isNullOrEmpty()) {
				mutableListOf()
			} else {
				request.purchaseOptions.toMutableList()
			},
			reportUsers = mutableListOf(),
			reportContents = mutableListOf()
		)
		val savedReview = reviewRepository.save(laterReview)
		account.addReview(savedReview.id)
		accountRepository.save(account)
		return ReviewResponse.fromReview(savedReview)
	}
}