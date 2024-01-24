package sosteam.deamhome.domain.review.service

import kotlinx.coroutines.reactor.awaitSingle
import sosteam.deamhome.domain.account.exception.AccountNotFoundException
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.review.exception.ReviewNotFoundException
import sosteam.deamhome.domain.review.exception.ReviewReportAlreadyExistException
import sosteam.deamhome.domain.review.handler.request.ReviewReportRequest
import sosteam.deamhome.domain.review.handler.response.ReviewResponse
import sosteam.deamhome.domain.review.repository.ReviewRepository

class ReviewReportService(
	private val reviewRepository: ReviewRepository,
	private val accountRepository: AccountRepository,
) {
	suspend fun updateReviewReport(request: ReviewReportRequest): ReviewResponse {
		accountRepository.findAccountByUserId(request.userId) ?: throw AccountNotFoundException()
		val review = reviewRepository.findById(request.reviewId).awaitSingle() ?: throw ReviewNotFoundException()
		val userReport = review.reportUsers.contains(request.userId)
		
		if (userReport) {
			throw ReviewReportAlreadyExistException()
		}
		review.reportUsers.add(request.userId)
		review.reportContent.add(request.reportContent)
		reviewRepository.save(review).awaitSingle()
		return ReviewResponse.fromReview(review)
	}
}