package sosteam.deamhome.domain.order.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class OrderNotFoundException (
    errorCode: String = "ORDER_NOT_FOUND",

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "주문 목록이 존재하지 않습니다."
    ):
    CustomGraphQLException(errorCode, ErrorType.NOT_FOUND, message) {
        override fun getMessage(): String = super.message
}