package sosteam.deamhome.domain.coupon.service

import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.coupon.entity.Coupon
import sosteam.deamhome.domain.coupon.entity.CouponType
import sosteam.deamhome.domain.coupon.exception.CouponNotFoundException
import sosteam.deamhome.domain.coupon.handler.request.CouponSearchRequest
import sosteam.deamhome.domain.coupon.repository.CouponRepository
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.exception.ItemNotFoundException
import sosteam.deamhome.domain.item.repository.ItemRepository

@Service
class CouponValidService(
	private val couponRepository: CouponRepository,
	private val itemRepository: ItemRepository
) {
	suspend fun validateCoupon(request: CouponSearchRequest): Pair<List<Coupon>, List<Item>> {
		val items = request.itemIds.map { itemRepository.findByPublicId(it) ?: throw ItemNotFoundException() }
		val categoryIds = items.map { it.categoryPublicId }
		val orderPrice = items.sumOf { it.price }
		
		val ableCoupons =
			couponRepository.findCoupons(request.userId, request.itemIds, categoryIds, request.links, orderPrice)
				.toList()
		if (ableCoupons.isNullOrEmpty()) {
			throw CouponNotFoundException()
		}
		
		val finalCoupons = validateBundleCoupon(items, ableCoupons)
		return Pair(finalCoupons, items)
	}
	
	private fun validateBundleCoupon(items: List<Item>, coupons: List<Coupon>): List<Coupon> {
		val itemPublicIds = items.map { it.publicId }.toSet()
		return coupons.filter { coupon ->
			if (coupon.couponType != CouponType.BUNDLE_DISCOUNT) true
			else {
				coupon.itemIds.filterNotNull().all { itemPublicIds.contains(it) }
			}
		}
	}
}