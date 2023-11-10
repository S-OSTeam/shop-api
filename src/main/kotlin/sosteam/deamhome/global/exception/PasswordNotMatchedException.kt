package sosteam.deamhome.global.exception

import org.springframework.graphql.execution.ErrorType

class PasswordNotMatchedException(
	errorCode: String = "PASSWORD_NOT_MATCHED",
	
	@JvmField
	@Suppress("INAPPLICABLE_JVM_FIELD")
	override val message: String = "입력된 비밀번호가 다릅니다."
) :
	CustomGraphQLException(errorCode, ErrorType.BAD_REQUEST, message) {
	override fun getMessage(): String = super.message
}