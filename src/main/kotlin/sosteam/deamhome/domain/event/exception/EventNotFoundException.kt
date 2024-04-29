package sosteam.deamhome.domain.event.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class EventNotFoundException (
    errorCode: String = "EVENT_NOT_FOUND_EXCEPTION",

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "유효하지 않은 이벤트입니다."
):
    CustomGraphQLException(errorCode, ErrorType.NOT_FOUND, message) {
    override fun getMessage(): String = super.message
}