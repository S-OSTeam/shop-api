package sosteam.deamhome.domain.item.entity

import lombok.Builder
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.entity.ItemDetailCategory
import sosteam.deamhome.domain.item.entity.dto.ItemDTO
import sosteam.deamhome.domain.item.resolver.request.ItemCreateRequest
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
	val status: Boolean = false
) : BaseEntity(){
	var images: MutableList<String> = mutableListOf()
	@DocumentReference(lazy = true) var account: Account? = null

	fun modifyImage(images: MutableList<String>): MutableList<String>{
		this.images = images
		return this.images
	}

	fun Item.toItemDTO(): ItemDTO {
		return ItemDTO(
			title = this.title,
			content = this.content,
			summary = this.summary,
			price = this.price,
			sellCnt = this.sellCnt,
			wishCnt = this.wishCnt,
			clickCnt = this.clickCnt,
			avgReview = this.avgReview,
			reviewCnt = this.reviewCnt,
			qnaCnt = this.qnaCnt,
			status = this.status,
			account = this.account,
			images = this.images.toList()
		)
	}

	companion object {
		fun fromRequest(request: ItemCreateRequest, account: Account): Item {
			return Item(
				title = request.title,
				content = request.content,
				summary = request.summary,
				price = request.price,
				sellCnt = request.sellCnt,
				wishCnt = request.wishCnt,
				clickCnt = request.clickCnt,
				avgReview = request.avgReview,
				reviewCnt = request.reviewCnt,
				qnaCnt = request.qnaCnt,
				status = request.status
			).apply { images = request.imageId }
		}

	}
}
