package sosteam.deamhome.domain.review.service

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactor.awaitSingleOrNull
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
	suspend fun searchReviewByReviewId(request: ReviewSearchRequest): ReviewResponse {
		isValidSize(request.id, 1)
		val review = reviewRepository.findById(request.id.get(0)).awaitSingleOrNull() ?: throw ReviewNotFoundException()
		return ReviewResponse.fromReview(review)
	}
	
	suspend fun searchReviewsByUserId(request: ReviewSearchRequest): List<ReviewResponse> {
		isValidSize(request.id, 1)
		val reviews = reviewRepository.findAllByUserId(request.id.get(0)).toList()
		return reviews.map { review ->
			ReviewResponse.fromReview(review)
		}
	}
	
	suspend fun searchReviewsByItemId(request: ReviewSearchRequest): List<ReviewResponse> {
		isValidSize(request.id, 1)
		val reviews = reviewRepository.findAllByItemId(request.id.get(0)).toList()
		return reviews.map { review ->
			ReviewResponse.fromReview(review)
		}
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