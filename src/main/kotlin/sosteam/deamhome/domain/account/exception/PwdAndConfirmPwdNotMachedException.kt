package sosteam.deamhome.domain.account.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class PwdAndConfirmPwdNotMachedException (
    errorCode: String = "PWD_NOT_MATCH",

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "비밀번호와 비밀번호 확인이 일치하지 않습니다."
) :
    CustomGraphQLException(errorCode, ErrorType.NOT_FOUND, message) {
    override fun getMessage(): String = super.message
}