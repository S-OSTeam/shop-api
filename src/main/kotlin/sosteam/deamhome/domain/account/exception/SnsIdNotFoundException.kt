package sosteam.deamhome.domain.account.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class SnsIdNotFoundException (
    errorCode: String = "SNSID_NOTFOUND",

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "회원 가입을 진행해 주세요"
) :
    CustomGraphQLException(errorCode, ErrorType.UNAUTHORIZED, message) {
    override fun getMessage(): String = super.message
}