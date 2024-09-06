package sosteam.deamhome.domain.item.handler.request

import com.github.f4b6a3.ulid.UlidCreator
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.entity.ItemStatus
import sosteam.deamhome.global.entity.DTO
import java.time.OffsetDateTime

data class ItemRequest(
	
	val categoryPublicId: String,
	val title: String,
	val content: String,
	val summary: String,
	val price: Int = 0,
	val status: ItemStatus,
	val storeId: String,
	val stockCnt: Int,
	val freeDelivery: Boolean = false,
	val option: ArrayList<String> = arrayListOf(),
	val productNumber: String,
	val deadline: OffsetDateTime,
	val originalWork: String,
	val material: String,
	val size: String,
	val weight: String,
	val shippingCost: Int,
	val imageUrls: List<String>

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
			storeId = this.storeId,
			stockCnt = this.stockCnt,
			freeDelivery = this.freeDelivery,
			option = this.option,
			productNumber = this.productNumber,
			deadline = this.deadline,
			originalWork = this.originalWork,
			material = this.material,
			size = this.size,
			weight = this.weight,
			shippingCost = this.shippingCost,
			imageUrls = this.imageUrls,
			sellCnt = 0,
			wishCnt = 0,
			clickCnt = 0,
			avgReview = 0.0,
			reviewCnt = 0,
			qnaCnt = 0,
			reviewScore = arrayListOf(0, 0, 0, 0, 0),
		)
	}
}