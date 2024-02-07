package sosteam.deamhome.domain.coupon.handler

import jakarta.validation.Valid
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.coupon.handler.request.CouponCreateRequest
import sosteam.deamhome.domain.coupon.handler.request.CouponSearchRequest
import sosteam.deamhome.domain.coupon.handler.response.CouponResponse
import sosteam.deamhome.domain.coupon.service.CouponCreateService
import sosteam.deamhome.domain.coupon.service.CouponSearchService
import sosteam.deamhome.domain.coupon.service.CouponValidService

@RestController
class CouponResolver(
	private val couponCreateService: CouponCreateService,
	private val couponSearchService: CouponSearchService,
	private val couponValidService: CouponValidService
) {
	@MutationMapping
	suspend fun createCoupon(@Argument @Valid request: CouponCreateRequest): CouponResponse {
		return couponCreateService.createCoupon(request)
	}
	
	@QueryMapping
	suspend fun searchCoupons(@Argument @Valid request: CouponSearchRequest): List<Map<String, CouponResponse>> {
		val couponAndItem = couponValidService.validateCoupon(request)
		return couponSearchService.searchCoupons(request, couponAndItem.first, couponAndItem.second)
	}
}