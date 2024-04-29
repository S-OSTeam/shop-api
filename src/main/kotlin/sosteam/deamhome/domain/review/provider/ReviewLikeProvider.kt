package sosteam.deamhome.domain.review.provider

import org.springframework.stereotype.Component
import sosteam.deamhome.domain.log.entity.ReviewLikeLog
import sosteam.deamhome.domain.log.repository.ReviewLikeLogRepository
import sosteam.deamhome.domain.review.entity.Review
import sosteam.deamhome.domain.review.entity.ReviewFavor
import sosteam.deamhome.domain.review.repository.ReviewRepository

@Component
class ReviewLikeProvider(
	private val reviewLikeLogRepository: ReviewLikeLogRepository,
	private val reviewRepository: ReviewRepository
) {
	suspend fun updateReviewLike(userId: String, reviewId: String, favor: ReviewFavor): Review {
		val review = reviewRepository.findByPublicId(reviewId)
		val userLiked = review.likeUsers.contains(userId)
		
		if (favor == ReviewFavor.LIKE && !userLiked) {
			review.likeUsers.add(userId)
		} else if ((favor == ReviewFavor.DISLIKE || favor == ReviewFavor.NONE) && userLiked) {
			review.likeUsers.remove(userId)
		}
		
		reviewRepository.save(review)
		
		val reviewLikeLog = ReviewLikeLog(
			id = null,
			reviewId = review.publicId,
			userId = userId,
			itemId = review.itemId,
			favor = favor.value
		)
		reviewLikeLogRepository.save(reviewLikeLog)
		
		return review
	}
}