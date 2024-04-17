package sosteam.deamhome.domain.payment.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class UnauthorizedSessionException (
	errorCode: String = "INVALID_SESSION",
	
	@JvmField
	@Suppress("INAPPLICABLE_JVM_FIELD")
	override val message: String = "승인되지 않은 세션입니다."
) :
	CustomGraphQLException(errorCode, ErrorType.UNAUTHORIZED, message) {
	override fun getMessage(): String = super.message
}