package sosteam.deamhome.domain.item.handler.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.http.codec.multipart.FilePart
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.global.entity.DTO

data class ItemRequest(

    @field: Min(1L)
    val categoryPublicId: Long,
    @field: NotBlank(message = "제목은 필수 입력 항목입니다.")
    @field: Size(max = 10, message = "제목 최대 길이는 10 입니다.")
    val title: String,
    @field: NotBlank(message = "상품 정보는 필수 입력 항목입니다.")
    val content: String,
    @field: NotBlank(message = "상품 요약은 필수 입력 항목입니다.")
    val summary: String,
    val price: Int = 0,
    val status: Boolean = false,
    @field: NotBlank(message = "판매자는 필수 입력 항목입니다.")
    val sellerId: String,
    val freeDelivery: Boolean = false,
    val images: List<FilePart>

) : DTO {
    override fun asDomain(): Item {
        return Item(
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
            //publicId 는 SequenceGenerator 로 할당
            //images 는 .apply 로 할당
        )
    }
}