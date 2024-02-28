package sosteam.deamhome.domain.review.handler.request

data class ReviewSearchRequest(
	val reviewId: List<String>,
	val userId: List<String>,
	val itemId: List<String>
)