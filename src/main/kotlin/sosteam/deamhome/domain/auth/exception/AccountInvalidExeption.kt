package sosteam.deamhome.domain.auth.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class AccountInvalidExeption (
    errorCode: String = "INVALID_ACCOUNT",

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "올바르지 않은 사용자입니다."
) :
    CustomGraphQLException(errorCode, ErrorType.BAD_REQUEST, message) {
    override fun getMessage(): String = super.message
}