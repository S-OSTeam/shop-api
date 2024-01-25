package sosteam.deamhome.domain.log.entity

import sosteam.deamhome.global.entity.LogEntity

class ReviewLikeLog(
	val reviewId: String,
	val userId: String,
	val itemId: String,
	val like: Boolean
) : LogEntity()