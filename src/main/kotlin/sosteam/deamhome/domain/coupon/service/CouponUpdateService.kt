package sosteam.deamhome.domain.coupon.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.coupon.entity.Coupon
import sosteam.deamhome.domain.coupon.handler.request.CouponUpdateRequest
import sosteam.deamhome.domain.coupon.handler.response.CouponResponse
import sosteam.deamhome.domain.coupon.repository.CouponRepository

@Service
class CouponUpdateService(
	private val couponRepository: CouponRepository
) {
	suspend fun updateCoupon(request: CouponUpdateRequest): CouponResponse {
		val originCoupon = couponRepository.findByPublicId(request.publicId)
		val updateCoupon = Coupon(
			id = originCoupon?.id,
			publicId = originCoupon!!.publicId,
			title = request.title,
			content = request.content,
			couponType = request.couponType,
			couponDiscountType = request.couponDiscountType,
			userId = request.userId,
			itemIds = request.itemIds,
			categoryIds = request.categoryIds,
			status = false,
			startDate = request.startDate,
			endDate = request.endDate,
			discount = request.discount,
			minPurchaseAmount = request.minPurchaseAmount,
			links = request.links,
		)
		val result = couponRepository.save(updateCoupon)
		return CouponResponse.fromCoupon(result)
	}
}