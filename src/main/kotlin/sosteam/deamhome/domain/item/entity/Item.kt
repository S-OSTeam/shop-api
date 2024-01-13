package sosteam.deamhome.domain.item.entity

import lombok.Builder
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import sosteam.deamhome.global.entity.BaseEntity
import sosteam.deamhome.global.image.entity.Image

@Document
@Builder
data class Item(
	@Indexed(unique = true)
	var publicId: Long = 0L,
	var categoryPublicId: Long = 0L,
	var title: String,
	var content: String,
	var summary: String,
	var price: Int = 0,
	var sellCnt: Int = 0,
	var wishCnt: Int = 0,
	var clickCnt: Int = 0,
	var stockCnt:Int = 0,
	val avgReview: Double = 0.0,
	val reviewCnt: Int = 0,
	val qnaCnt: Int = 0,
	val status: Boolean = false,
	//account 의 userId 는 절대로 안바뀌겠지???
	val sellerId: String,
	val freeDelivery: Boolean = false

) : BaseEntity(){
	var images: MutableList<Image> = mutableListOf()

	fun modifyImage(images: MutableList<Image>): MutableList<Image>{
		this.images = images
		return this.images
	}

}
