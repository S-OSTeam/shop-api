package sosteam.deamhome.domain.review.handler.request

data class ReviewCreateRequest(
	val title: String,
	val parentPublicId: String?,
	val content: String,
	val score: Int,
	val status: Boolean,
	val userId: String,
	val itemId: String,
	val images: List<String>?,
	val purchaseOptions: List<String>?
)