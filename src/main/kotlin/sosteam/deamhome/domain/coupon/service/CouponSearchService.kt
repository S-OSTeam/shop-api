package sosteam.deamhome.domain.coupon.service

import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.coupon.entity.Coupon
import sosteam.deamhome.domain.coupon.entity.CouponType
import sosteam.deamhome.domain.coupon.handler.request.CouponSearchRequest
import sosteam.deamhome.domain.coupon.handler.response.CouponResponse
import sosteam.deamhome.domain.coupon.repository.CouponRepository
import sosteam.deamhome.domain.item.exception.ItemNotFoundException
import sosteam.deamhome.domain.item.repository.ItemRepository

@Service
class CouponSearchService(
	private val couponRepository: CouponRepository,
	private val itemRepository: ItemRepository
) {
	suspend fun searchCoupons(request: CouponSearchRequest): List<CouponResponse> {
		val coupons = couponRepository.findCoupons(request.userId, request.itemId).toList()
		val sortedCoupons = sortCoupons(coupons, request)
		return sortedCoupons.map { CouponResponse.fromCoupon(it) }
	}
	
	private suspend fun sortCoupons(coupons: List<Coupon>, request: CouponSearchRequest): List<Coupon> {
		val item = itemRepository.findByPublicId(request.itemId) ?: throw ItemNotFoundException()
		return coupons
			.map { coupon ->
				val discountedPrice = when (coupon.couponType) {
					CouponType.PERCENTAGE_DISCOUNT -> item.price - (item.price * coupon.discount / 100)
					CouponType.FIXED_AMOUNT_DISCOUNT -> item.price - coupon.discount
				}
				coupon to discountedPrice
			}
			.sortedBy { it.second }
			.map { it.first }
	}
}