package sosteam.deamhome.domain.account.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class AccountDeletedException (
    errorCode: String = "ACCOUNT_ALREADY_DELETE",

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "삭제 된 회원입니다."
    ) :
    CustomGraphQLException(errorCode, ErrorType.FORBIDDEN, message) {
        override fun getMessage(): String = super.message
}