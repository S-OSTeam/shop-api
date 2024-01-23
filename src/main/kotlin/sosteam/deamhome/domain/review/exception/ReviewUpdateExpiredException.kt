package sosteam.deamhome.domain.review.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class ReviewUpdateExpiredException(
	errorCode: String = "UPDATE_DATE_EXPIRED",
	
	@JvmField
	@Suppress("INAPPLICABLE_JVM_FIELD")
	override val message: String = "리뷰 수정 기한을 지났습니다."
) : CustomGraphQLException(errorCode, ErrorType.BAD_REQUEST, message) {
	override fun getMessage(): String = super.message
}