package sosteam.deamhome.domain.review.handler.response

import sosteam.deamhome.domain.review.entity.Review

data class ReviewResponse(
	val reviewId: String,
	val title: String,
	val content: String,
	val score: Double,
	val status: Boolean,
	val userId: String,
	val itemId: String,
	val images: List<String>,
	val likeUsers: List<String>
) {
	companion object {
		fun fromReview(review: Review): ReviewResponse {
			return ReviewResponse(
				reviewId = review.id,
				title = review.title,
				content = review.content,
				score = review.score,
				status = review.status,
				userId = review.userId,
				itemId = review.itemId,
				images = review.images.map { it.fileUrl },
				likeUsers = review.likeUsers,
			)
		}
	}
}