package sosteam.deamhome.domain.account.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class AccountNotLiveException(
	errorCode: String = "ACCOUNT_NOT_LIVE_STATUS",
	
	@JvmField
	@Suppress("INAPPLICABLE_JVM_FIELD")
	override val message: String = "회원이 라이브 상태가 아닙니다."
) :
	CustomGraphQLException(errorCode, ErrorType.BAD_REQUEST, message) {
	override fun getMessage(): String = super.message
}