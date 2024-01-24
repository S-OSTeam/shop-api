package sosteam.deamhome.domain.review.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class ReviewReportAbuseBlockedException(
	errorCode: String = "REPORT_ABUSE_BLOCKED",
	
	@JvmField
	@Suppress("INAPPLICABLE_JVM_FIELD")
	override val message: String = "신고 기능을 이용할 수 없습니다."
) : CustomGraphQLException(errorCode, ErrorType.UNAUTHORIZED, message) {
	override fun getMessage(): String = super.message
}