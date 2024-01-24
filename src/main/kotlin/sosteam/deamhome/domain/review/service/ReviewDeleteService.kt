package sosteam.deamhome.domain.review.service

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.exception.AccountNotFoundException
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.item.exception.ItemNotFoundException
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.domain.review.exception.ReviewDeletionTimeLimitExceededException
import sosteam.deamhome.domain.review.exception.ReviewNotFoundException
import sosteam.deamhome.domain.review.handler.request.ReviewDeleteRequest
import sosteam.deamhome.domain.review.repository.ReviewRepository
import sosteam.deamhome.global.image.provider.ImageProvider
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class ReviewDeleteService(
	private val reviewRepository: ReviewRepository,
	private val accountRepository: AccountRepository,
	private val itemRepository: ItemRepository,
	private val imageProvider: ImageProvider
) {
	suspend fun deleteReview(request: ReviewDeleteRequest): ResponseEntity<String> {
		val review = reviewRepository.findById(request.reviewId).awaitSingleOrNull() ?: throw ReviewNotFoundException()
		review.images.forEach {
			imageProvider.deleteImage(it.fileUrl)
		}
		
		val account = accountRepository.findAccountByUserId(review.userId) ?: throw AccountNotFoundException()
		val createdAt = review.getCreatedAt()
		val now = LocalDateTime.now()
		// 리뷰 생성 후 일주일이 지났는지 확인
		if (ChronoUnit.DAYS.between(createdAt, now) > 7) {
			throw ReviewDeletionTimeLimitExceededException()
		}
		account.removeReview(review.id)
		accountRepository.save(account).awaitSingle()
		
		val item = itemRepository.findItemById(review.itemId) ?: throw ItemNotFoundException()
		item.avgReview = (item.avgReview * item.reviewCnt - review.score) / (item.reviewCnt - 1)
		item.reviewCnt--
		itemRepository.save(item).awaitSingle()
		
		reviewRepository.deleteById(request.reviewId).awaitSingleOrNull()
		return ResponseEntity.ok("Review 제거 성공 : `${request.reviewId}` ")
	}
}