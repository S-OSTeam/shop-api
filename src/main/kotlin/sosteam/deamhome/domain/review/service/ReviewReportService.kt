package sosteam.deamhome.domain.review.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.review.entity.Review
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
		val review: Review = reviewRepository.findByPublicId(request.reviewId)
		val userReport = review.reportUsers.contains(request.userId)
		
		val now = LocalDateTime.now()
		account.addReviewReportLog(now)
		accountRepository.save(account)
		
		if (userReport) {
			throw ReviewReportAlreadyExistException()
		}
		review.reportUsers.add(request.userId)
		review.reportContents.add(request.reportContent)
		
		// 신고 개수가 10개 이상이면 리뷰 제한
		if (review.reportUsers.size >= 10) {
			review.status = false
		}
		
		reviewRepository.save(review)
		return ReviewResponse.fromReview(review)
	}
}