package sosteam.deamhome.domain.review.handler.request

data class ReviewReportRequest(
	val reviewId: String,
	val userId: String,
	val reportContent: String
)
