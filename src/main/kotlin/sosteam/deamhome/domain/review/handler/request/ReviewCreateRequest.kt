package sosteam.deamhome.domain.review.handler.request

import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class ReviewCreateRequest(
	@field: NotBlank(message = "제목은 필수 입력 항목입니다.")
	@field: Size(max = 10, message = "제목 최대 길이는 10 입니다.")
	val title: String,
	@field: NotBlank(message = "리뷰 내용은 필수 입력 항목입니다.")
	val content: String,
	@field: DecimalMin(value = "0")
	@field: DecimalMax(value = "5")
	val score: Double,
	val status: Boolean,
	@field: NotBlank(message = "유저 ID는 필수 입력 항목입니다.")
	val userId: String,
	@field: NotBlank(message = "아이템 ID는 필수 입력 항목입니다.")
	val itemId: String,
	val images: List<String>,
)