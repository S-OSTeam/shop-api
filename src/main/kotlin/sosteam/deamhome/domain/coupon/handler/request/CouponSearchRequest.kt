package sosteam.deamhome.domain.coupon.handler.request

data class CouponSearchRequest(
	val userId: String,
	val itemIds: List<String>,
	val links: List<String?>
)