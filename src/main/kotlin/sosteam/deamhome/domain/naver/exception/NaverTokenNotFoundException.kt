package sosteam.deamhome.domain.naver.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class NaverTokenNotFoundException(
    errorCode: String = "NAVER_TOKEN_NOT_FOUND",

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "Naver Token을 받을 수 없습니다."
) : CustomGraphQLException(errorCode, ErrorType.NOT_FOUND, message) {
    override fun getMessage(): String = super.message
}