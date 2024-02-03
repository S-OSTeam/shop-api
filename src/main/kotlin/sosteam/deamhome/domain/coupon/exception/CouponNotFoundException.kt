package sosteam.deamhome.domain.coupon.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class CouponNotFoundException(
	errorCode: String = "COUPON_NOT_FOUND",
	
	@JvmField
	@Suppress("INAPPLICABLE_JVM_FIELD")
	override val message: String = "사용할 수 있는 쿠폰이 존재하지 않습니다."
) : CustomGraphQLException(errorCode, ErrorType.NOT_FOUND, message) {
	override fun getMessage(): String = super.message
}