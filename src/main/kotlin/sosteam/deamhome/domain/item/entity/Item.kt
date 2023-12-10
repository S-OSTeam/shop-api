package sosteam.deamhome.domain.item.entity

import lombok.Builder
import org.springframework.data.mongodb.core.mapping.Document
import sosteam.deamhome.global.entity.BaseEntity
import sosteam.deamhome.global.image.entity.Image

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
	val sellerId: String
) : BaseEntity(){
	var images: MutableList<Image> = mutableListOf()

	fun modifyImage(images: MutableList<Image>): MutableList<Image>{
		this.images = images
		return this.images
	}

}
