package sosteam.deamhome.domain.coupon.handler.request

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import sosteam.deamhome.domain.coupon.entity.CouponDiscountType
import sosteam.deamhome.domain.coupon.entity.CouponType
import java.time.LocalDateTime

data class CouponCreateRequest(
	val title: @NotBlank(message = "쿠폰 제목은 필수 입력 사항입니다.") String,
	val content: @NotBlank(message = "쿠폰 내용은 필수 입력 사항입니다.") String,
	val couponType: @NotBlank(message = "쿠폰 타입은 필수 입력 사항입니다.") CouponType,
	val couponDiscountType: @NotBlank(message = "쿠폰 할인 타입은 필수 입력 사항입니다.") CouponDiscountType,
	val userId: String?,
	val itemIds: List<String?> = emptyList(),
	val categoryIds: List<String?> = emptyList(),
	val startDate: LocalDateTime?,
	val endDate: LocalDateTime?,
	val discount: @DecimalMin(value = "0") Int,
	val minPurchaseAmount: Int? = null,
	val links: List<String?> = emptyList()
)