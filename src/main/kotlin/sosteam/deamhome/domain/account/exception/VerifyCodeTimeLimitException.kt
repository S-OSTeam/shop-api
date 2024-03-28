package sosteam.deamhome.domain.account.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class VerifyCodeTimeLimitException (
    errorCode: String = "VERIFYCODE_TIMELIMIT_EXCEPTION",

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "인증 코드 재발급은 2분이 지나야 할 수 있습니다."
) :
    CustomGraphQLException(errorCode, ErrorType.BAD_REQUEST, message) {
    override fun getMessage(): String = super.message
}