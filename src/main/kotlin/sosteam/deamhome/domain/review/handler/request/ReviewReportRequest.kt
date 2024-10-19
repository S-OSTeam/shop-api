package sosteam.deamhome.domain.review.handler.request

data class ReviewReportRequest(
	val publicId: String,
	val userId: String,
	val reportContent: String
)
