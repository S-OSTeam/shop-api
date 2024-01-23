 package sosteam.deamhome.domain.review.entity

import lombok.Builder
import lombok.Setter
//import org.springframework.data.mongodb.core.mapping.Document
//import org.springframework.data.mongodb.core.mapping.DocumentReference
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.global.entity.LogEntity
import sosteam.deamhome.global.image.entity.Image

 // TODO postgreSQL 로 바꾸기
//@Document
//@Builder
class Review(
	var title: String,
	var content: String,
	var score: Int = 0,
	var status: Boolean = false,
	val userId: String,
	val itemId: String,
	var images: MutableList<Image>,
	var likeUsers: MutableList<String>,
	var purchaseOptions: MutableList<String>
) : LogEntity()
