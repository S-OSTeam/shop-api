package sosteam.deamhome.domain.review.handler.request

data class ReviewLikeRequest(
	val reviewId: String,
	val userId: String,
	val favor: Boolean,
)