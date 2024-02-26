package sosteam.deamhome.domain.review.handler.request

import jakarta.validation.constraints.NotBlank

data class ReviewDeleteRequest(
	@field: NotBlank(message = "Review ID는 필수 입력 항목입니다.")
	val reviewId: String,
)