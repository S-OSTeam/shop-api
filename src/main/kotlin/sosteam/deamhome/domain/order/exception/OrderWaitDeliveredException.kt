package sosteam.deamhome.domain.order.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class OrderWaitDeliveredException(
	errorCode: String = "ORDER_WAIT_DELIVERED",
	
	@JvmField
	@Suppress("INAPPLICABLE_JVM_FIELD")
	override val message: String = "배송 도착을 기다리고 있습니다."
) :
	CustomGraphQLException(errorCode, ErrorType.BAD_REQUEST, message) {
	override fun getMessage(): String = super.message
}