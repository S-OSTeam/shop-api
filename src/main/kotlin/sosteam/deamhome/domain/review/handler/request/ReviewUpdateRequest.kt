package sosteam.deamhome.domain.review.handler.request

data class ReviewUpdateRequest(
	val publicId: String,
	val title: String,
	val content: String,
	val score: Int,
	val status: Boolean,
	val beforeImageUrls: List<String>,
	val afterImageUrls: List<String>,
	val likeUsers: List<String>,
	val purchaseOptions: List<String>
)