package sosteam.deamhome.domain.review.service

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.review.handler.request.ReviewDeleteRequest
import sosteam.deamhome.domain.review.repository.ReviewRepository

@Service
class ReviewDeleteService(
	private val reviewRepository: ReviewRepository
) {
	suspend fun deleteReview(request: ReviewDeleteRequest): ResponseEntity<String> {
		reviewRepository.deleteById(request.reviewId).awaitSingleOrNull()
		return ResponseEntity.ok("Review 제거 성공 : `${request.reviewId}` ")
	}
}