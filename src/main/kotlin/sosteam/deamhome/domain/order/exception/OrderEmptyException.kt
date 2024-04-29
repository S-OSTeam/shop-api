package sosteam.deamhome.domain.order.exception

import sosteam.deamhome.global.exception.CustomGraphQLException
import org.springframework.graphql.execution.ErrorType

class OrderEmptyException (
    errorCode: String = "ORDER_WAIT_DELIVERED",

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "주문 상품이 존재하지 않습니다."
):
    CustomGraphQLException(errorCode, ErrorType.BAD_REQUEST, message) {
        override fun getMessage(): String = super.message
}