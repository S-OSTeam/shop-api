package sosteam.deamhome.domain.coupon.handler.request

import jakarta.validation.constraints.NotBlank

data class CouponSearchRequest(
	val userId: @NotBlank(message = "User ID는 필수 입력 사항입니다.") String,
	val categoryIds: @NotBlank(message = "Category ID는 필수 입력 사항입니다.") List<String>,
	val itemIds: @NotBlank(message = "Item ID는 필수 입력 사항입니다.") List<String>,
)