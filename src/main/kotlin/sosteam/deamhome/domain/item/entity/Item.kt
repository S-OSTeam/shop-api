package sosteam.deamhome.domain.item.entity

import lombok.Builder
import lombok.Setter
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.entity.ItemDetailCategory
import sosteam.deamhome.global.entity.Image
import sosteam.deamhome.global.entity.LogEntity

@Document
@Builder
class Item(
	var title: String,
	var content: String,
	var summary: String,
	var price: Int = 0,
	var sellCnt: Int = 0,
	var wishCnt: Int = 0,
	var clickCnt: Int = 0,
	val avgReview: Double = 0.0,
	val reviewCnt: Int = 0,
	val qnaCnt: Int = 0,
	val status: Boolean = false,
	account: Account,
	itemCategory: ItemCategory,
	itemDetailCategory: ItemDetailCategory,
	images: List<Image>
) : LogEntity() {
	
	@DBRef(lazy = true)
	@Setter
	val account: Account = account
	
	@DBRef(lazy = true)
	var images: ArrayList<Image> = images as ArrayList<Image>
	
	@DBRef(lazy = true)
	@Setter
	val itemCategory: ItemCategory = itemCategory
	
	@DBRef(lazy = true)
	@Setter
	private val itemDetailCategory: ItemDetailCategory = itemDetailCategory
	
	fun addImage(image: Image): List<Image> {
		images.add(image)
		return images
	}
}