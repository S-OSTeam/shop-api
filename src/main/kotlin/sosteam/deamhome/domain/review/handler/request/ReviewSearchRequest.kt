package sosteam.deamhome.domain.review.handler.request

data class ReviewSearchRequest(
	val publicId: List<String>,
	val userId: List<String>,
	val itemId: List<String>
)