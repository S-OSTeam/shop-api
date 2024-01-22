package sosteam.deamhome.domain.review.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class ReviewIllegalArgumentIdException(
	errorCode: String = "ILLEGAL_ARGUMENT_ID",
	
	@JvmField
	@Suppress("INAPPLICABLE_JVM_FIELD")
	override val message: String = "ID의 개수가 조건에 맞지 않습니다."
) : CustomGraphQLException(errorCode, ErrorType.NOT_FOUND, message) {
	override fun getMessage(): String = super.message
}