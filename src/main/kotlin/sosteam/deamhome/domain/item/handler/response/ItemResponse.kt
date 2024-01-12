package sosteam.deamhome.domain.item.handler.response

import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.global.image.entity.Image

class ItemResponse (
    val publicId: Long,
    val categoryPublicId: Long,
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
    val status: Boolean,
    val sellerId: String,
    val freeDelivery: Boolean,
    val imageUrls: List<String>
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
                imageUrls = item.images.map { it.fileUrl }
            )
        }
    }
}