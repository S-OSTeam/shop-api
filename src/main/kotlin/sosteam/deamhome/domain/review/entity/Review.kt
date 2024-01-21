 package sosteam.deamhome.domain.review.entity

import lombok.Builder
import lombok.Setter
//import org.springframework.data.mongodb.core.mapping.Document
//import org.springframework.data.mongodb.core.mapping.DocumentReference
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.global.entity.LogEntity

 // TODO postgreSQL 로 바꾸기
//@Document
//@Builder
class Review(
	var title: String,
	var content: String,
	var like: Int = 0,
	var score: Double = 0.0,
	var status: Boolean = false,
	var account: Account,
	var item: Item,
	var images: List<String>,
	var likeUsers: List<String>,
) : LogEntity()
