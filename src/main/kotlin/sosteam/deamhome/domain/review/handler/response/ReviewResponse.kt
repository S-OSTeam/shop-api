package sosteam.deamhome.domain.review.handler.response

import sosteam.deamhome.domain.review.entity.Review
import sosteam.deamhome.global.image.entity.Image

data class ReviewResponse(
	val reviewId: String,
	val title: String,
	val content: String,
	val like: Int,
	val score: Double,
	val status: Boolean,
	val userId: String,
	val itemId: String,
	val images: List<Image>,
	val likeUsersId: List<String>
) {
	companion object {
		fun fromReview(review: Review): ReviewResponse {
			return ReviewResponse(
				reviewId = review.id,
				title = review.title,
				content = review.content,
				like = review.like,
				score = review.score,
				status = review.status,
				userId = review.account.userId,
				itemId = review.item.id,
				images = review.images,
				likeUsersId = review.likeUsers,
			)
		}
	}
}
