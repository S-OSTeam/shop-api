package sosteam.deamhome.domain.review.service

import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.review.handler.request.ReviewSearchRequest
import sosteam.deamhome.domain.review.handler.response.ReviewResponse
import sosteam.deamhome.domain.review.repository.ReviewRepository

@Service
class ReviewSearchService(
	private val reviewRepository: ReviewRepository
) {
	suspend fun searchReviews(request: ReviewSearchRequest): List<ReviewResponse> {
		val responses = mutableListOf<ReviewResponse>()
		
		if (request.reviewId.isNotEmpty()) {
			request.reviewId.forEach {
				val reviews = reviewRepository.findReviews(it, null, null).toList()
				responses.addAll(reviews.map { review -> ReviewResponse.fromReview(review) })
			}
		}
		if (request.userId.isNotEmpty()) {
			request.userId.forEach {
				val reviews = reviewRepository.findReviews(null, it, null).toList()
				responses.addAll(reviews.map { review -> ReviewResponse.fromReview(review) })
			}
		}
		if (request.itemId.isNotEmpty()) {
			request.itemId.forEach {
				val reviews = reviewRepository.findReviews(null, null, it).toList()
				responses.addAll(reviews.map { review -> ReviewResponse.fromReview(review) })
			}
		}
		
		return responses.distinct()
	}
}