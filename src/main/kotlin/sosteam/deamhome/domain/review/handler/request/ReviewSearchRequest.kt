package sosteam.deamhome.domain.review.handler.request

import jakarta.validation.constraints.NotBlank

data class ReviewSearchRequest(
	@field: NotBlank(message = "ID(review|user|item)는 필수 입력 사항입니다.")
	val id: String
)