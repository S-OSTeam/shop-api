package sosteam.deamhome.domain.review.entity

import lombok.Builder
import lombok.Setter
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.global.entity.LogEntity
import sosteam.deamhome.global.image.entity.Image

@Document
@Builder
class Review(
	var title: String,
	var content: String,
	var like: Int = 0,
	var score: Double = 0.0,
	var status: Boolean = false,
	account: Account,
	item: Item,
	images: List<Image>,
	likeUsers: List<String>
) : LogEntity() {
	
	@DocumentReference(lazy = true)
	var images: ArrayList<Image> = images as ArrayList<Image>
	
	@DocumentReference(lazy = true)
	@Setter
	var item: Item = item
	
	@DocumentReference(lazy = true)
	@Setter
	var account: Account = account
	
	@DocumentReference(lazy = true)
	val likeUsers: ArrayList<String> = likeUsers as ArrayList<String>
	
	fun addImage(image: Image): List<Image> {
		images.add(image)
		return images
	}
	
	fun remove(image: Image): List<Image> {
		images.remove(image)
		return images
	}
	
	fun addLikeUser(userId: String): List<String> {
		likeUsers.add(userId)
		return likeUsers
	}
	
	fun removeLikeUser(userId: String): List<String> {
		likeUsers.remove(userId)
		return likeUsers
	}
}