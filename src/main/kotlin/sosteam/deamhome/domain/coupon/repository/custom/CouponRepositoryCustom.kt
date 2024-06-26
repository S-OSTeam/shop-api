package sosteam.deamhome.domain.coupon.repository.custom

import kotlinx.coroutines.flow.Flow
import sosteam.deamhome.domain.coupon.entity.Coupon

interface CouponRepositoryCustom {
	fun findCoupons(
		userId: String?,
		itemIds: List<String?>,
		categoryIds: List<String?>,
		links: List<String?>,
		orderPrice: Int
	): Flow<Coupon>
}