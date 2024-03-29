package sosteam.deamhome.domain.category.handler.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.global.entity.DTO

data class ItemCategoryRequest (

    @field: NotBlank(message = "제목은 필수 입력 항목입니다.")
    @field: Size(max = 30, message = "제목 최대 길이는 30 입니다.")
    val title: String,
    @field: Min(1L)
    val parentPublicId: Long?
): DTO {
    override fun asDomain(): ItemCategory {
        return ItemCategory(
            // publicId 는 sequence 로 생성
            publicId = 0L,
            title = this.title,
            parentPublicId = this.parentPublicId ?: 0L
        )
    }
}