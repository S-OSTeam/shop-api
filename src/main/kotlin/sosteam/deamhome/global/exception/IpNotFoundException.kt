package sosteam.deamhome.global.exception

import org.springframework.graphql.execution.ErrorType

class IpNotFoundException(
	errorCode: String = "IP_NOT_FOUND",
	
	@JvmField
	@Suppress("INAPPLICABLE_JVM_FIELD")
	override val message: String = "IP주소를 입력 해주세요."
) :
	CustomGraphQLException(errorCode, ErrorType.BAD_REQUEST, message) {
	override fun getMessage(): String = super.message
}