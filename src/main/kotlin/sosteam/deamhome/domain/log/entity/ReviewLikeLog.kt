package sosteam.deamhome.domain.log.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.entity.LogEntity

@Table("review_like_log")
class ReviewLikeLog(
	@Id
	val id: Long?,
	val reviewId: Long,
	val userId: String,
	val itemId: String,
	val like: Boolean
) : LogEntity()