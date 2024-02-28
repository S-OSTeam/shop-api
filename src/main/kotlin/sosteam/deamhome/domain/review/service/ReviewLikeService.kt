package sosteam.deamhome.domain.review.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.exception.AccountNotFoundException
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.review.handler.request.ReviewLikeRequest
import sosteam.deamhome.domain.review.handler.response.ReviewResponse
import sosteam.deamhome.domain.review.provider.ReviewLikeProvider

@Service
class ReviewLikeService(
	private val accountRepository: AccountRepository,
	private val reviewLikeProvider: ReviewLikeProvider
) {
	suspend fun updateReviewLike(request: ReviewLikeRequest): ReviewResponse {
		accountRepository.findAccountByUserId(request.userId) ?: throw AccountNotFoundException()
		val review = reviewLikeProvider.updateReviewLike(request.userId, request.reviewId, request.favor)
		
		return ReviewResponse.fromReview(review)
	}
}