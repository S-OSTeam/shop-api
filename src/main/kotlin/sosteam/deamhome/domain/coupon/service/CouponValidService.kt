package sosteam.deamhome.domain.coupon.service

import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.coupon.entity.Coupon
import sosteam.deamhome.domain.coupon.exception.CouponNotFoundException
import sosteam.deamhome.domain.coupon.handler.request.CouponSearchRequest
import sosteam.deamhome.domain.coupon.repository.CouponRepository
import sosteam.deamhome.domain.item.repository.ItemRepository

@Service
class CouponValidService(
	private val couponRepository: CouponRepository,
	private val itemRepository: ItemRepository
) {
	suspend fun validateCoupon(request: CouponSearchRequest): Pair<List<Coupon>, List<String>> {
		val itemsByCategory = itemRepository.findByCategoryPublicIdIn(request.categoryIds).toList()
		val categoryItemIds = itemsByCategory.map { it.publicId }
		val items = request.itemIds + categoryItemIds
		
		val ableCoupons = couponRepository.findCoupons(request.userId, items).toList()
		if (ableCoupons.isNullOrEmpty()) {
			throw CouponNotFoundException()
		}
		return Pair(ableCoupons, items)
	}
}