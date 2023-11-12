package sosteam.deamhome.global.exception

import org.springframework.graphql.execution.ErrorType

class MacNotFoundException(
	errorCode: String = "MAC_NOT_FOUND",
	
	@JvmField
	@Suppress("INAPPLICABLE_JVM_FIELD")
	override val message: String = "MAC주소를 입력 해주세요."
) :
	CustomGraphQLException(errorCode, ErrorType.BAD_REQUEST, message) {
	override fun getMessage(): String = super.message
}