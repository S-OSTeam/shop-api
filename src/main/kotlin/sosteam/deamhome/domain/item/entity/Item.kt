package sosteam.deamhome.domain.item.entity

import lombok.Builder
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.entity.ItemDetailCategory
import sosteam.deamhome.global.entity.BaseEntity
import sosteam.deamhome.global.entity.Image

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
	@DocumentReference(lazy = true) var account: Account?,
	@DocumentReference(lazy = true) var itemCategory: ItemCategory?,
	@DocumentReference(lazy = true) var itemDetailCategory: ItemDetailCategory?,
	@DocumentReference(lazy = true) var images: List<Image>?
) : BaseEntity()