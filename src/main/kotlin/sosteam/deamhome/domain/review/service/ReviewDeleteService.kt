package sosteam.deamhome.domain.review.service

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.ResponseEntity
import sosteam.deamhome.domain.review.handler.request.ReviewDeleteRequest
import sosteam.deamhome.domain.review.repository.ReviewRepository

class ReviewDeleteService(
	private val reviewRepository: ReviewRepository
) {
	suspend fun deleteReview(request: ReviewDeleteRequest): ResponseEntity<String> {
		reviewRepository.deleteById(request.reviewId).awaitSingle()
		return ResponseEntity.ok("Review 제거 성공 : `${request.reviewId}` ")
	}
}