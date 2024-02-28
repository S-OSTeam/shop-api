package sosteam.deamhome.domain.review.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.exception.AccountNotFoundException
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.log.entity.ReviewLikeLog
import sosteam.deamhome.domain.log.repository.ReviewLikeLogRepository
import sosteam.deamhome.domain.review.handler.request.ReviewLikeRequest
import sosteam.deamhome.domain.review.handler.response.ReviewResponse
import sosteam.deamhome.domain.review.repository.ReviewRepository

@Service
class ReviewLikeService(
	private val reviewRepository: ReviewRepository,
	private val accountRepository: AccountRepository,
	private val reviewLikeLogRepository: ReviewLikeLogRepository
) {
	suspend fun updateReviewLike(request: ReviewLikeRequest): ReviewResponse {
		accountRepository.findAccountByUserId(request.userId) ?: throw AccountNotFoundException()
		val review = reviewRepository.findByPublicId(request.reviewId)
		val userLiked = review.likeUsers.contains(request.userId)
		
		if (request.favor && !userLiked) {
			review.likeUsers.add(request.userId)
		} else if (!request.favor && userLiked) {
			review.likeUsers.remove(request.userId)
		}
		reviewRepository.save(review)
		
		val reviewLikeLog = ReviewLikeLog(
			id = null,
			reviewId = request.reviewId,
			userId = request.userId,
			itemId = review.itemId,
			favor = request.favor
		)
		reviewLikeLogRepository.save(reviewLikeLog)
		
		return ReviewResponse.fromReview(review)
	}
}