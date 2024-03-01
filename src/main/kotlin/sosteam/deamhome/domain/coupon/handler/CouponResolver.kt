package sosteam.deamhome.domain.coupon.handler

import jakarta.validation.Valid
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.coupon.handler.request.CouponCreateRequest
import sosteam.deamhome.domain.coupon.handler.request.CouponDeleteRequest
import sosteam.deamhome.domain.coupon.handler.request.CouponSearchRequest
import sosteam.deamhome.domain.coupon.handler.request.CouponUpdateRequest
import sosteam.deamhome.domain.coupon.handler.response.CouponEntryResponse
import sosteam.deamhome.domain.coupon.handler.response.CouponResponse
import sosteam.deamhome.domain.coupon.service.*

@RestController
class CouponResolver(
	private val couponCreateService: CouponCreateService,
	private val couponSearchService: CouponSearchService,
	private val couponValidService: CouponValidService,
	private val couponUpdateService: CouponUpdateService,
	private val couponDeleteService: CouponDeleteService
) {
	@MutationMapping
	suspend fun createCoupon(@Argument @Valid request: CouponCreateRequest): CouponResponse {
		return couponCreateService.createCoupon(request)
	}
	
	@QueryMapping
	suspend fun searchCoupons(@Argument @Valid request: CouponSearchRequest): List<CouponEntryResponse> {
		val couponAndItem = couponValidService.validateCoupon(request)
		return couponSearchService.searchCoupons(request, couponAndItem.first, couponAndItem.second)
	}
	
	@MutationMapping
	suspend fun updateCoupon(@Argument @Valid request: CouponUpdateRequest): CouponResponse {
		return couponUpdateService.updateCoupon(request)
	}
	
	@MutationMapping
	suspend fun deleteCoupon(@Argument @Valid request: CouponDeleteRequest): Long {
		return couponDeleteService.deleteCoupon(request)
	}
}