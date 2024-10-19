package sosteam.deamhome.domain.review.handler.response

import sosteam.deamhome.domain.review.entity.Review

data class ReviewResponse(
	val publicId: String,
	val title: String,
	val parentPublicId: String,
	val content: String,
	val score: Int,
	val status: Boolean,
	val userId: String,
	val itemId: String,
	val imageUrls: List<String>?,
	val likeUsers: Int?,
	val purchaseOptions: List<String>?
) {
	companion object {
		fun fromReview(review: Review): ReviewResponse {
			return ReviewResponse(
				publicId = review.publicId,
				title = review.title,
				parentPublicId = review.parentPublicId,
				content = review.content,
				score = review.score,
				status = review.status,
				userId = review.userId,
				itemId = review.itemId,
				imageUrls = review.imageUrls,
				likeUsers = review.likeUsers.size,
				purchaseOptions = review.purchaseOptions
			)
		}
	}
}