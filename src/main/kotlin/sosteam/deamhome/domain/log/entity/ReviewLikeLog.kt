package sosteam.deamhome.domain.log.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.entity.LogEntity

@Table("review_like_log")
class ReviewLikeLog(
	@Id
	var id: Long?,
	@Column("review_id")
	val reviewId: String,
	@Column("user_id")
	val userId: String,
	@Column("item_id")
	val itemId: String,
	@Column("favor")
	val favor: Int?
) : LogEntity()