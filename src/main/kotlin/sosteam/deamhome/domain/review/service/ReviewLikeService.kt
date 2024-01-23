package sosteam.deamhome.domain.review.service

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.exception.AccountNotFoundException
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.review.exception.ReviewNotFoundException
import sosteam.deamhome.domain.review.handler.request.ReviewLikeRequest
import sosteam.deamhome.domain.review.handler.response.ReviewResponse
import sosteam.deamhome.domain.review.repository.ReviewRepository

@Service
class ReviewLikeService(
	private val reviewRepository: ReviewRepository,
	private val accountRepository: AccountRepository,
) {
	suspend fun updateReviewLike(request: ReviewLikeRequest): ReviewResponse {
		accountRepository.findAccountByUserId(request.userId) ?: throw AccountNotFoundException()
		val review = reviewRepository.findById(request.reviewId).awaitSingle() ?: throw ReviewNotFoundException()
		val userLiked = review.likeUsers.contains(request.userId)
		
		if (request.like && !userLiked) {
			review.likeUsers.add(request.userId)
		} else if (!request.like && userLiked) {
			review.likeUsers.remove(request.userId)
		}
		reviewRepository.save(review).awaitSingle()
		return ReviewResponse.fromReview(review)
	}
}