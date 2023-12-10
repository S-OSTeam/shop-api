package sosteam.deamhome.domain.account.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class AccountNotFoundException(
	errorCode: String = "ACCOUNT_NOT_FOUND",
	
	@JvmField
	@Suppress("INAPPLICABLE_JVM_FIELD")
	override val message: String = "가입되지 않은 회원입니다."
) :
	CustomGraphQLException(errorCode, ErrorType.NOT_FOUND, message) {
	override fun getMessage(): String = super.message
}