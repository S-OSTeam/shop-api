package sosteam.deamhome.domain.review.handler.request

import sosteam.deamhome.global.image.handler.request.ImageRequest

data class ReviewUpdateRequest(
	val reviewId: String,
	val title: String,
	val content: String,
	val score: Int,
	val status: Boolean,
	val originImageUrls: List<String>,
	val addImages: List<ImageRequest>,
	val likeUsers: List<String>,
	val purchaseOptions: List<String>
)