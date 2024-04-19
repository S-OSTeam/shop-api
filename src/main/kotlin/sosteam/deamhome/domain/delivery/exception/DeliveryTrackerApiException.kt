package sosteam.deamhome.domain.delivery.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class DeliveryTrackerApiException (
	errorCode: String = "DELIVERY_TRACKER_API_EXCEPTION",
	
	@JvmField
	@Suppress("INAPPLICABLE_JVM_FIELD")
	override val message: String = "Delivery Tracker Api 를 호출하는 데 실패했습니다."
) :
	CustomGraphQLException(errorCode, ErrorType.INTERNAL_ERROR, message) {
	override fun getMessage(): String = super.message
}