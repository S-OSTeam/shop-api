package sosteam.deamhome.domain.review.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class ReviewPolicyViolationException(
	errorCode: String = "REVIEW_POLICY_VIOLATION",
	
	@JvmField
	@Suppress("INAPPLICABLE_JVM_FIELD")
	override val message: String = "리뷰 정책을 위반하여 계정이 정지됩니다."
) : CustomGraphQLException(errorCode, ErrorType.FORBIDDEN, message) {
	override fun getMessage(): String = super.message
}