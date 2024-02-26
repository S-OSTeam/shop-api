package sosteam.deamhome.domain.review.handler.request

import jakarta.validation.constraints.NotBlank

data class ReviewMonthRequest(
	@field: NotBlank(message = "Review ID는 필수 입력 항목입니다.")
	val reviewId: Long,
	@field: NotBlank(message = "1달 후 후기 내용은 필수 입력 항목입니다.")
	val monthReview: String
)
