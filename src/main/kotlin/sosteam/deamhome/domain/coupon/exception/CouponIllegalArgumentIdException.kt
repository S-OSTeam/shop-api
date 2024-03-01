package sosteam.deamhome.domain.coupon.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class CouponIllegalArgumentIdException(
	errorCode: String = "ILLEGAL_ARGUMENT_ID",
	
	@JvmField
	@Suppress("INAPPLICABLE_JVM_FIELD")
	override val message: String = "ID가 조건에 맞지 않습니다."
) : CustomGraphQLException(errorCode, ErrorType.BAD_REQUEST, message) {
	override fun getMessage(): String = super.message
}