package sosteam.deamhome.domain.review.service

import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.review.exception.ReviewIllegalArgumentIdException
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
	
	suspend fun searchReviewByUserAndItemId(request: ReviewSearchRequest): List<ReviewResponse> {
		isValidSize(request.id, 2)
		val reviews = reviewRepository.findAllByUserAndItemId(request.id.get(0), request.id.get(1)).toList()
		return reviews.map { review ->
			ReviewResponse.fromReview(review)
		}
	}
	
	fun isValidSize(list: List<String>, listSize: Int) {
		if (list.size != listSize) {
			throw ReviewIllegalArgumentIdException()
		}
	}
}