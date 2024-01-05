package sosteam.deamhome.domain.account.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class EmailAndPasswordNotMachedException (
    errorCode: String = "EMAIL_PASSWORD_NOT_MATCHED",

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "아이디 비밀번호가 일치하지 않습니다."
    ) :
    CustomGraphQLException(errorCode, ErrorType.UNAUTHORIZED, message) {
        override fun getMessage(): String = super.message
}