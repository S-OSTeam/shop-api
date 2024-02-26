package sosteam.deamhome.domain.event.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class InvalidEventTimeException (
    errorCode: String = "INVALID_EVENT_TIME_EXCEPTION",

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "이벤트 시작시간은 종료시간보다 앞서야 합니다"
):
    CustomGraphQLException(errorCode, ErrorType.NOT_FOUND, message) {
    override fun getMessage(): String = super.message
}