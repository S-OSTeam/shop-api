package sosteam.deamhome.domain.review.service

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.review.handler.request.ReviewSearchRequest
import sosteam.deamhome.domain.review.handler.response.ReviewResponse
import sosteam.deamhome.domain.review.repository.ReviewRepository

@Service
class ReviewSearchService(
	private val reviewRepository: ReviewRepository
) {
	suspend fun searchReviewById(request: ReviewSearchRequest): ReviewResponse {
		val review = reviewRepository.findById(request.id).awaitSingle()
		return ReviewResponse.fromReview(review)
	}
	
	suspend fun searchReviewsByUserId(request: ReviewSearchRequest): List<ReviewResponse> {
		val reviews = reviewRepository.findAllByUserId(request.id).toList()
		return reviews.map { review ->
			ReviewResponse.fromReview(review)
		}
	}
	
	suspend fun searchReviewsByItemId(request: ReviewSearchRequest): List<ReviewResponse> {
		val reviews = reviewRepository.findAllByItemId(request.id).toList()
		return reviews.map { review ->
			ReviewResponse.fromReview(review)
		}
	}
}