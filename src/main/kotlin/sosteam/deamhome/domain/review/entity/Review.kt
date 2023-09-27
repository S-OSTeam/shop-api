package sosteam.deamhome.domain.review.entity

import lombok.Builder
import lombok.Setter
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.global.entity.Image
import sosteam.deamhome.global.entity.LogEntity

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
	images: List<Image>
) : LogEntity() {
	
	@DBRef(lazy = true)
	val images: ArrayList<Image> = images as ArrayList<Image>
	
	@DBRef(lazy = true)
	@Setter
	var item: Item = item
	
	@DBRef(lazy = true)
	@Setter
	var account: Account = account
	
	fun addImage(image: Image): List<Image> {
		images.add(image)
		return images
	}
}