package sosteam.deamhome.domain.review.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.entity.LogEntity

@Table("review")
class Review(
	@Id
	var id: Long?,
	var title: String,
	var content: String,
	var monthReview: String,
	var score: Int = 0,
	var status: Boolean = false,
	val userId: String,
	val itemId: String,
	var imageUrls: MutableList<String>,
	var likeUsers: MutableList<String>,
	var purchaseOptions: MutableList<String>,
	var reportUsers: MutableList<String>,
	var reportContent: MutableList<String>
) : LogEntity()
