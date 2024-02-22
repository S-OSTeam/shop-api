package sosteam.deamhome.domain.coupon.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.coupon.handler.request.CouponDeleteRequest
import sosteam.deamhome.domain.coupon.repository.CouponRepository

@Service
class CouponDeleteService(
	private val couponRepository: CouponRepository
) {
	suspend fun deleteCoupon(request: CouponDeleteRequest): Long {
		return couponRepository.deleteByPublicId(request.publicId)
	}
}