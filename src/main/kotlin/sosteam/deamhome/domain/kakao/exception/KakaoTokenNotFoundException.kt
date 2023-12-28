package sosteam.deamhome.domain.kakao.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class KakaoTokenNotFoundException(
    errorCode: String = "KAKAO_TOKEN_NOT_FOUND",

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "cannot recieve kako Token."
) : CustomGraphQLException(errorCode, ErrorType.NOT_FOUND, message) {
    override fun getMessage(): String = super.message
}