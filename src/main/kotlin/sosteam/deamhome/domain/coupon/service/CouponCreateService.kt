package sosteam.deamhome.domain.coupon.service

import com.github.f4b6a3.ulid.UlidCreator
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.coupon.entity.Coupon
import sosteam.deamhome.domain.coupon.handler.request.CouponCreateRequest
import sosteam.deamhome.domain.coupon.handler.response.CouponResponse
import sosteam.deamhome.domain.coupon.repository.CouponRepository

@Service
class CouponCreateService(
	private val couponRepository: CouponRepository
) {
	suspend fun createCoupon(request: CouponCreateRequest): CouponResponse {
		val publicId = UlidCreator.getMonotonicUlid().toString().replace("-", "")
		val coupon = Coupon(
			id = null,
			publicId = publicId,
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
		val result = couponRepository.save(coupon)
		return CouponResponse.fromCoupon(result)
	}
}