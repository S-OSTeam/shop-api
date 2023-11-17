package sosteam.deamhome.domain.item.entity.dto.request

import org.springframework.http.codec.multipart.FilePart
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.global.entity.DTO

data class ItemRequestDTO(
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
    val categoryTitle: String,
    val detailCategoryTitle: String,
    val sellerId: String,
    val images: List<FilePart>
) : DTO {
    override fun asDomain(): Item {
        return Item(
            this.title,
            this.content,
            this.summary,
            this.price,
            this.sellCnt,
            this.wishCnt,
            this.clickCnt,
            this.avgReview,
            this.reviewCnt,
            this.qnaCnt,
            this.status,
            this.categoryTitle,
            this.detailCategoryTitle,
            this.sellerId
        )
    }
}