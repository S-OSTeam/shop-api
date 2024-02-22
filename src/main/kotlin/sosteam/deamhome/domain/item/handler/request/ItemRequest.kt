package sosteam.deamhome.domain.item.handler.request

import com.github.f4b6a3.ulid.UlidCreator
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.global.entity.DTO
import sosteam.deamhome.global.image.handler.request.ImageRequest
import java.time.OffsetDateTime

data class ItemRequest(

	val categoryPublicId: String,
	val title: String,
	val content: String,
	val summary: String,
	val price: Int = 0,
	val status: Boolean = false,
	val sellerId: String,
	val freeDelivery: Boolean = false,
	val option: ArrayList<String> = arrayListOf(),
	val productNumber: String,
	val deadline: OffsetDateTime,
	val originalWork: String,
	val material: String,
	val size: String,
	val weight: String,
	val shippingCost: Int,
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
			option = this.option,
			productNumber = this.productNumber,
			deadline = this.deadline,
			originalWork = this.originalWork,
			material = this.material,
			size = this.size,
			weight = this.weight,
			shippingCost = this.shippingCost,
			sellCnt = 0,
			wishCnt = 0,
			clickCnt = 0,
			avgReview = 0.0,
			reviewCnt = 0,
			qnaCnt = 0,
			reviewScore = arrayListOf(0, 0, 0, 0, 0),
			//imageUrls 는 .apply 로 할당
		)
	}
}