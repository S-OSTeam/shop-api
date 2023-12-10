package sosteam.deamhome.domain.account.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class AlreadyExistAccountException(
	errorCode: String = "ALREADY_EXIST_ACCOUNT",
	
	@JvmField
	@Suppress("INAPPLICABLE_JVM_FIELD")
	override val message: String = "이미 가입 된 회원입니다."
) :
	CustomGraphQLException(errorCode, ErrorType.BAD_REQUEST, message) {
	override fun getMessage(): String = super.message
}