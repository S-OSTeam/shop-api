package sosteam.deamhome.domain.review.service

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.exception.AccountNotFoundException
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.item.exception.ItemNotFoundException
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.domain.review.handler.request.ReviewDeleteRequest
import sosteam.deamhome.domain.review.repository.ReviewRepository
import sosteam.deamhome.global.image.provider.ImageProvider

@Service
class ReviewDeleteService(
	private val reviewRepository: ReviewRepository,
	private val accountRepository: AccountRepository,
	private val itemRepository: ItemRepository,
	private val imageProvider: ImageProvider
) {
	suspend fun deleteReview(request: ReviewDeleteRequest): ResponseEntity<String> {
		val review = reviewRepository.findByPublicId(request.publicId)
		
		review.imageUrls.forEach {
			imageProvider.deleteImageByUrl(it)
		}
		
		val account = accountRepository.findAccountByUserId(review.userId) ?: throw AccountNotFoundException()
		account.removeReview(review.id)
		accountRepository.save(account)
		
		val item = itemRepository.findByPublicId(review.itemId) ?: throw ItemNotFoundException()
		item.avgReview = (item.avgReview * item.reviewCnt - review.score) / (item.reviewCnt - 1)
		item.reviewCnt--
		itemRepository.save(item)
		
		reviewRepository.deleteByPublicId(request.publicId)
		return ResponseEntity.ok("Review 제거 성공 : `${request.publicId}` ")
	}
}