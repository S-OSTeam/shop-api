package sosteam.deamhome.domain.review.service

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.review.entity.Review
import sosteam.deamhome.domain.review.exception.ProductUsageTimeNotReachedException
import sosteam.deamhome.domain.review.exception.ReviewDeletionTimeLimitExceededException
import sosteam.deamhome.domain.review.exception.ReviewNotFoundException
import sosteam.deamhome.domain.review.handler.request.ReviewDeleteRequest
import sosteam.deamhome.domain.review.handler.request.ReviewMonthRequest
import sosteam.deamhome.domain.review.repository.ReviewRepository
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class ReviewValidService(
	private val reviewRepository: ReviewRepository
) {
	suspend fun validateMonthReview(request: ReviewMonthRequest): Review {
		// TODO: 우선 리뷰 작성 한달 뒤로 했는데, 제품이 배달되고 1달 뒤로 바꿔야 할 거 같음. order쪽이 완성 되야 할 수 있을듯?
		val review = reviewRepository.findById(request.reviewId).awaitSingle() ?: throw ReviewNotFoundException()
		
		val createdAt = review.getCreatedAt()
		val now = LocalDateTime.now()
		
		// 리뷰 생성 후 한달이 지났는지 확인
		if (ChronoUnit.MONTHS.between(createdAt, now) <= 1) {
			throw ProductUsageTimeNotReachedException()
		}
		return review
	}
	
	suspend fun validateDeleteReview(request: ReviewDeleteRequest): Review {
		val review = reviewRepository.findById(request.reviewId).awaitSingleOrNull() ?: throw ReviewNotFoundException()
		
		val createdAt = review.getCreatedAt()
		val now = LocalDateTime.now()
		// 리뷰 생성 후 일주일이 지났는지 확인
		if (ChronoUnit.DAYS.between(createdAt, now) > 7) {
			throw ReviewDeletionTimeLimitExceededException()
		}
		return review
	}
}