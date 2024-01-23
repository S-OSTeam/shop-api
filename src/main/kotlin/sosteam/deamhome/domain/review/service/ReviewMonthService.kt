package sosteam.deamhome.domain.review.service

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.review.exception.ReviewNotFoundException
import sosteam.deamhome.domain.review.handler.request.ReviewMonthRequest
import sosteam.deamhome.domain.review.handler.response.ReviewResponse
import sosteam.deamhome.domain.review.repository.ReviewRepository

@Service
class ReviewMonthService(
	private val reviewRepository: ReviewRepository
) {
	suspend fun updateMonthReview(request: ReviewMonthRequest): ReviewResponse {
		val review = reviewRepository.findById(request.reviewId).awaitSingle() ?: throw ReviewNotFoundException()
		if (review.monthReview == request.monthReview) {
			return ReviewResponse.fromReview(review)
		}
		review.monthReview = request.monthReview
		reviewRepository.save(review).awaitSingle()
		return ReviewResponse.fromReview(review)
	}
}