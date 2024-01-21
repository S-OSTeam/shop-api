package sosteam.deamhome.domain.item.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.entity.BaseEntity

@Table("item")
data class Item(
	@Id
	var id: Long?,
	var publicId: String,
	var categoryPublicId: String,
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
	var imageUrls: MutableList<String> = mutableListOf()

}
