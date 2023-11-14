package sosteam.deamhome.domain.item.entity

import lombok.Builder
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.global.entity.BaseEntity

@Document
@Builder
data class Item(
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
	val categoryTitle: String,
	val detailCategoryTitle: String
) : BaseEntity(){
	var images: MutableList<String> = mutableListOf()
	@DocumentReference(lazy = true) var account: Account? = null

	fun modifyImage(images: MutableList<String>): MutableList<String>{
		this.images = images
		return this.images
	}

}
