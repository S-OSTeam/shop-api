package sosteam.deamhome.domain.review.entity

import org.springframework.data.mongodb.core.mapping.Document
import sosteam.deamhome.global.entity.LogEntity
import sosteam.deamhome.global.image.entity.Image

@Document
class Review(
	var title: String,
	var content: String,
	var monthReview: String,
	var score: Int = 0,
	var status: Boolean = false,
	val userId: String,
	val itemId: String,
	var images: MutableList<Image>,
	var likeUsers: MutableList<String>,
	var purchaseOptions: MutableList<String>,
	var reportUsers: MutableList<String>,
	var reportContent: MutableList<String>
) : LogEntity()