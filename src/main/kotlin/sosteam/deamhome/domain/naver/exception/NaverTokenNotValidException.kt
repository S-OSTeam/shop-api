package sosteam.deamhome.domain.naver.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class NaverTokenNotValidException(
    errorCode: String = "NAVER_TOKEN_NOT_VALID",

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "Naver Token이 유효하지 않습니다.."
) : CustomGraphQLException(errorCode, ErrorType.UNAUTHORIZED, message) {
    override fun getMessage(): String = super.message
}