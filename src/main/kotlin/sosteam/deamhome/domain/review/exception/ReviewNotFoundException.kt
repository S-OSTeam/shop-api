package sosteam.deamhome.domain.review.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class ReviewNotFoundException(
	@JvmField
	@Suppress("INAPPLICABLE_JVM_FIELD")
	override val message: String = "존재하지 않는 리뷰입니다."
) : CustomGraphQLException("REVIE_NOT_FOUND", ErrorType.NOT_FOUND, message) {
	override fun getMessage(): String = super.message
}