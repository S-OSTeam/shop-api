package sosteam.deamhome.domain.review.handler.request

import sosteam.deamhome.global.image.handler.request.ImageRequest

data class ReviewCreateRequest(
	val title: String,
	val content: String,
	val score: Int,
	val status: Boolean,
	val userId: String,
	val itemId: String,
	val images: List<ImageRequest>,
	val purchaseOptions: List<String>
)