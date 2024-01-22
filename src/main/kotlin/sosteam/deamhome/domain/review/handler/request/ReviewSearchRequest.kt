package sosteam.deamhome.domain.review.handler.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

data class ReviewSearchRequest(
	val reviewId: List<@NotBlank(message = "Review ID는 필수 입력 사항입니다.") Long>,
	val userId: List<@NotBlank(message = "User ID는 필수 입력 사항입니다.") String>,
	val itemId: List<@NotBlank(message = "Item ID는 필수 입력 사항입니다.") String>
)