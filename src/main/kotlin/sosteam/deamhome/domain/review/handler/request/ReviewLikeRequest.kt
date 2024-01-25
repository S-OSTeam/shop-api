package sosteam.deamhome.domain.review.handler.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ReviewLikeRequest(
	@field: NotBlank(message = "Review ID는 필수 입력 항목입니다.")
	val reviewId: Long,
	@field: NotBlank(message = "유저 ID는 필수 입력 항목입니다.")
	val userId: String,
	@field: NotNull
	val like: Boolean
)