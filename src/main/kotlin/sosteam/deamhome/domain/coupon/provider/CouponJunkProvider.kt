package sosteam.deamhome.domain.coupon.provider

import sosteam.deamhome.domain.coupon.entity.Coupon
import sosteam.deamhome.domain.coupon.entity.CouponDiscountType
import sosteam.deamhome.domain.coupon.entity.CouponType
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.entity.ItemStatus
import java.time.OffsetDateTime

class CouponJunkProvider {
	companion object {
		fun createJunkItem(): Item {
			val PRICE = 112345678
			return Item(
				id = null,
				publicId = "",
				categoryPublicId = "",
				title = "",
				content = "",
				summary = "",
				price = PRICE,
				sellCnt = 0,
				wishCnt = 0,
				clickCnt = 0,
				stockCnt = 0,
				avgReview = 0.0,
				reviewCnt = 0,
				qnaCnt = 0,
				status = ItemStatus.AVAILABLE,
				storeId = "",
				freeDelivery = false,
				reviewScore = listOf(0, 0, 0, 0, 0),
				option = listOf(),
				productNumber = "0",
				deadline = OffsetDateTime.MAX,
				originalWork = "",
				material = "",
				size = "",
				weight = "",
				shippingCost = 0,
			)
		}
		
		fun createJunkCoupon(): Coupon {
			val DISCOUNT = -112345678
			return Coupon(
				id = null,
				publicId = "",
				title = "",
				content = "",
				couponType = CouponType.USER_SPECIFIC,
				couponDiscountType = CouponDiscountType.FIXED_AMOUNT_DISCOUNT,
				userId = "",
				itemIds = listOf(),
				categoryIds = listOf(),
				status = false,
				startDate = OffsetDateTime.now(),
				endDate = OffsetDateTime.now(),
				discount = DISCOUNT,
				minPurchaseAmount = 0,
				links = listOf()
			)
		}
	}
}