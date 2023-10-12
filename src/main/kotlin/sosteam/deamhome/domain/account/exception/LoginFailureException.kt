package sosteam.deamhome.domain.account.exception

import graphql.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class LoginFailureException(
	errorCode: String = "LOGIN_FAILURE_EXCEPTION",
	
	@JvmField
	@Suppress("INAPPLICABLE_JVM_FIELD")
	override val message: String = "message"
) :
	CustomGraphQLException(errorCode, ErrorType.InvalidSyntax, message) {
	override fun getMessage(): String = super.message
}