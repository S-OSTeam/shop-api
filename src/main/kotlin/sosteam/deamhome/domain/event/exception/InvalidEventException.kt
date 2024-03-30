package sosteam.deamhome.domain.event.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class InvalidEventException (
    errorCode: String = "INVALID_EVENT_EXCEPTION",

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "이벤트 요청이 올바르지 않습니다."
):
    CustomGraphQLException(errorCode, ErrorType.BAD_REQUEST, message) {
    override fun getMessage(): String = super.message
}