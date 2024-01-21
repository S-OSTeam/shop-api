package sosteam.deamhome.domain.review.service

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.review.handler.request.ReviewUpdateRequest
import sosteam.deamhome.domain.review.handler.response.ReviewResponse
import sosteam.deamhome.domain.review.repository.ReviewRepository

@Service
class ReviewUpdateService(
	private val reviewRepository: ReviewRepository
) {
	suspend fun updateReview(request: ReviewUpdateRequest): ReviewResponse {
		val originReview = reviewRepository.findById(request.reviewId).awaitSingle()
		
		val updatedReview = originReview.apply {
			title = request.title
			content = request.content
			score = request.score
			status = request.status
			images = request.images
			likeUsers = request.likeUsers
		}
		reviewRepository.save(updatedReview).awaitSingle()
		return ReviewResponse.fromReview(updatedReview)
	}
}