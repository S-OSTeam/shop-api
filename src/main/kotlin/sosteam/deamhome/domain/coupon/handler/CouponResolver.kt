package sosteam.deamhome.domain.coupon.handler

import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.coupon.handler.request.CouponSearchRequest
import sosteam.deamhome.domain.coupon.handler.response.CouponResponse
import sosteam.deamhome.domain.coupon.service.CouponSearchService
import sosteam.deamhome.domain.coupon.service.CouponValidService

@RestController
class CouponResolver(
	private val couponSearchService: CouponSearchService,
	private val couponValidService: CouponValidService
) {
	@QueryMapping
	suspend fun searchCoupons(request: CouponSearchRequest): List<Map<String, CouponResponse>> {
		val couponAndItem = couponValidService.validateCoupon(request)
		return couponSearchService.searchCoupons(request, couponAndItem.first, couponAndItem.second)
	}
}