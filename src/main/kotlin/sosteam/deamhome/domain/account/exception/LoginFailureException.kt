package sosteam.deamhome.domain.account.exception

import graphql.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class LoginFailureException(
	errorCode: String = "LOGIN_FAILURE_EXCEPTION",
	
	@JvmField
	@Suppress("INAPPLICABLE_JVM_FIELD")
	override val message: String = "가입되지 않은 회원이거나 아이디와 비밀번호가 일치하지 않습니다."
) :
	CustomGraphQLException(errorCode, ErrorType.InvalidSyntax, message) {
	override fun getMessage(): String = super.message
}