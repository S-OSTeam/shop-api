package sosteam.deamhome.domain.coupon.entity

enum class CouponType {
	USER_SPECIFIC,
	PRODUCT_SPECIFIC,
	TIME_LIMITED,
	FIRST_COME_FIRST_SERVED,
	FIRST_PURCHASE,
	NTH_PURCHASE,
	MIN_PURCHASE_AMOUNT,
	COUPON_NUMBER_REGISTRATION,
	CATEGORY_SPECIFIC,
	EVENT_BASED,
	REFERRAL,
	MEMBERSHIP_LEVEL,
	SHIPPING_DISCOUNT,
	BUNDLE_DISCOUNT
}