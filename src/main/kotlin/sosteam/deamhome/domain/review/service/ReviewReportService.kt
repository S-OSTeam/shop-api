package sosteam.deamhome.domain.review.service

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.review.exception.ReviewNotFoundException
import sosteam.deamhome.domain.review.exception.ReviewReportAlreadyExistException
import sosteam.deamhome.domain.review.handler.request.ReviewReportRequest
import sosteam.deamhome.domain.review.handler.response.ReviewResponse
import sosteam.deamhome.domain.review.repository.ReviewRepository
import java.time.LocalDateTime

@Service
class ReviewReportService(
	private val reviewRepository: ReviewRepository,
	private val accountRepository: AccountRepository
) {
	suspend fun updateReviewReport(request: ReviewReportRequest, account: Account): ReviewResponse {
		val review = reviewRepository.findById(request.reviewId).awaitSingle() ?: throw ReviewNotFoundException()
		val userReport = review.reportUsers.contains(request.userId)
		
		val now = LocalDateTime.now()
		account.addReviewReportLog(now)
		accountRepository.save(account).awaitSingle()
		
		if (userReport) {
			throw ReviewReportAlreadyExistException()
		}
		review.reportUsers.add(request.userId)
		review.reportContent.add(request.reportContent)
		
		// 신고 개수가 100개 이상이면 리뷰 제한
		if (review.reportUsers.size >= 100) {
			review.status = false
		}
		
		reviewRepository.save(review).awaitSingle()
		return ReviewResponse.fromReview(review)
	}
}