package sosteam.deamhome.domain.review.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class ReviewProductUsageTimeNotReachedException(
	errorCode: String = "PRODUCT_USAGE_TIME_NOT_REACHED",
	
	@JvmField
	@Suppress("INAPPLICABLE_JVM_FIELD")
	override val message: String = "제품을 사용한지 한달이 지나지 않았습니다."
) : CustomGraphQLException(errorCode, ErrorType.BAD_REQUEST, message) {
	override fun getMessage(): String = super.message
}