package sosteam.deamhome.domain.item.entity.dto.response

import sosteam.deamhome.domain.item.entity.Item

//TODO account 추가해야함
class ItemResponseDTO (
    val title: String,
    val content: String,
    val summary: String,
    val price: Int = 0,
    val sellCnt: Int = 0,
    val wishCnt: Int = 0,
    val clickCnt: Int = 0,
    val avgReview: Double = 0.0,
    val reviewCnt: Int = 0,
    val qnaCnt: Int = 0,
    val status: Boolean = false,
    val imageId: MutableList<String>,
    val categoryTitle: String,
    val detailCategoryTitle: String,
) {
    companion object {
        fun fromItem(item: Item): ItemResponseDTO {
            return ItemResponseDTO(
                title = item.title,
                content = item.title,
                summary = item.summary,
                price = item.price,
                sellCnt = item.sellCnt,
                wishCnt = item.wishCnt,
                clickCnt = item.clickCnt,
                avgReview = item.avgReview,
                reviewCnt = item.reviewCnt,
                qnaCnt = item.qnaCnt,
                status = item.status,
                imageId = item.images,
                categoryTitle = item.categoryTitle,
                detailCategoryTitle = item.detailCategoryTitle
            )
        }
    }
}