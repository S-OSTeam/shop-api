package sosteam.deamhome.domain.item.handler.request

import com.github.f4b6a3.ulid.UlidCreator
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.global.entity.DTO
import sosteam.deamhome.global.image.handler.request.ImageRequest

data class ItemRequest(
	
	val categoryPublicId: String,
	val title: String,
	val content: String,
	val summary: String,
	val price: Int = 0,
	val status: Boolean = false,
	val sellerId: String,
	val freeDelivery: Boolean = false,
	val images: List<ImageRequest>

) : DTO {
	override fun asDomain(): Item {
		val publicId = UlidCreator.getMonotonicUlid().toString().replace("-", "")
		return Item(
			// id 는 save 하고 postgreSQL bigSerial 으로 자동 생성
			id = null,
			publicId = publicId,
			categoryPublicId = this.categoryPublicId,
			title = this.title,
			content = this.content,
			summary = this.summary,
			price = this.price,
			status = this.status,
			sellerId = this.sellerId,
			freeDelivery = this.freeDelivery,
			sellCnt = 0,
			wishCnt = 0,
			clickCnt = 0,
			avgReview = 0.0,
			reviewCnt = 0,
			qnaCnt = 0
			//imageUrls 는 .apply 로 할당
		)
	}
}