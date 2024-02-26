package sosteam.deamhome.domain.item.handler.response

import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.global.attribute.ItemStatus
import java.time.OffsetDateTime

class ItemResponse (
    val publicId: String,
    val categoryPublicId: String,
    val title: String,
    val content: String,
    val summary: String,
    val price: Int,
    val sellCnt: Int,
    val wishCnt: Int,
    val clickCnt: Int,
    val avgReview: Double,
    val reviewCnt: Int,
    val qnaCnt: Int,
    val status: ItemStatus,
    val sellerId: String,
    val freeDelivery: Boolean,
    val imageUrls: List<String>,
    val stockCnt: Int,
    val reviewScore: List<Int>,
    val option: List<String>,
    val productNumber: String,
    val deadline: OffsetDateTime,
    val originalWork: String,
    val material: String,
    val size: String,
    val weight: String,
    val shippingCost: Int,
) {
    companion object {
        fun fromItem(item: Item): ItemResponse {
            return ItemResponse(
                publicId = item.publicId,
                categoryPublicId = item.categoryPublicId,
                title = item.title,
                content = item.content,
                summary = item.summary,
                price = item.price,
                sellCnt = item.sellCnt,
                wishCnt = item.wishCnt,
                clickCnt = item.clickCnt,
                avgReview = item.avgReview,
                reviewCnt = item.reviewCnt,
                qnaCnt = item.qnaCnt,
                status = item.status,
                sellerId = item.sellerId,
                freeDelivery = item.freeDelivery,
                stockCnt = item.stockCnt,
                imageUrls = item.imageUrls,
                reviewScore = item.reviewScore,
                option = item.option,
                productNumber = item.productNumber,
                deadline = item.deadline,
                originalWork = item.originalWork,
                material = item.material,
                size = item.size,
                weight = item.weight,
                shippingCost = item.shippingCost
            )
        }
    }
}