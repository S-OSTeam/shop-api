package sosteam.deamhome.domain.coupon.handler.request

import jakarta.validation.constraints.NotBlank

data class CouponDeleteRequest(
	val publicId: @NotBlank(message = "쿠폰 publicId는 필수 입력 사항입니다.") String
)