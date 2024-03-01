package sosteam.deamhome.domain.review.service

import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.review.exception.ReviewNotFoundException
import sosteam.deamhome.domain.review.handler.request.ReviewSearchRequest
import sosteam.deamhome.domain.review.handler.response.ReviewResponse
import sosteam.deamhome.domain.review.repository.ReviewRepository

@Service
class ReviewSearchService(
	private val reviewRepository: ReviewRepository
) {
	suspend fun searchReviews(request: ReviewSearchRequest): List<ReviewResponse> {
		val reviews = reviewRepository.findReviews(request.reviewId, request.userId, request.itemId)
			.toList()
		
		if (reviews.isEmpty()) {
			throw ReviewNotFoundException()
		}
		
		return reviews.map { ReviewResponse.fromReview(it) }
	}
}