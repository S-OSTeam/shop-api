package sosteam.deamhome.domain.item.entity.dto.request

import org.springframework.http.codec.multipart.FilePart
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.global.entity.DTO

data class ItemRequestDTO(
    val title: String,
    val content: String,
    val summary: String,
    val price: Int = 0,
    val status: Boolean = false,
    val categoryTitle: String,
    val detailCategoryTitle: String,
    val sellerId: String,
    val images:
    List<FilePart>
) : DTO {
    override fun asDomain(): Item {
        return Item(
            this.title,
            this.content,
            this.summary,
            this.price,
            sellCnt = 0,
            wishCnt = 0,
            clickCnt = 0,
            avgReview = 0.0,
            reviewCnt = 0,
            qnaCnt = 0,
            this.status,
            this.sellerId
        //images 는 .apply 로 할당
        )
    }
}