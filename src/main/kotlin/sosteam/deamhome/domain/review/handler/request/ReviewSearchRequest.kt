package sosteam.deamhome.domain.review.handler.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

data class ReviewSearchRequest(
	@field:NotEmpty(message = "입력이 아예 비어있습니다.")
	val id: List<@NotBlank(message = "ID(review|user|item)는 필수 입력 사항입니다.") String>
)