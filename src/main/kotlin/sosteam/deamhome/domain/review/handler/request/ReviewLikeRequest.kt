package sosteam.deamhome.domain.review.handler.request

import sosteam.deamhome.domain.review.entity.ReviewFavor

data class ReviewLikeRequest(
	val publicId: String,
	val userId: String,
	val favor: ReviewFavor,
)