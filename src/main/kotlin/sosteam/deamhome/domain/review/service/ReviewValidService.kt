package sosteam.deamhome.domain.review.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.exception.AccountNotFoundException
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.review.entity.Review
import sosteam.deamhome.domain.review.exception.*
import sosteam.deamhome.domain.review.handler.request.ReviewDeleteRequest
import sosteam.deamhome.domain.review.handler.request.ReviewMonthRequest
import sosteam.deamhome.domain.review.handler.request.ReviewReportRequest
import sosteam.deamhome.domain.review.handler.request.ReviewUpdateRequest
import sosteam.deamhome.domain.review.repository.ReviewRepository
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class ReviewValidService(
	private val reviewRepository: ReviewRepository,
	private val accountRepository: AccountRepository
) {
	suspend fun validateUpdateReview(request: ReviewUpdateRequest): Review {
		val review = reviewRepository.findByPublicId(request.reviewId)
		
		val createdAt = review.getCreatedAt()
		val now = LocalDateTime.now()
		
		// 리뷰 생성 후 일주일이 지났는지 확인
		if (ChronoUnit.DAYS.between(createdAt, now) > 7) {
			throw ReviewUpdateExpiredException()
		}
		return review
	}
	
	suspend fun validateMonthReview(request: ReviewMonthRequest): Review {
		// TODO: 우선 리뷰 작성 한달 뒤로 했는데, 제품이 배달되고 1달 뒤로 바꿔야 할 거 같음. order쪽이 완성 되야 할 수 있을듯?
		val review = reviewRepository.findByPublicId(request.reviewId)
		
		val createdAt = review.getCreatedAt()
		val now = LocalDateTime.now()
		
		// 리뷰 생성 후 한달이 지났는지 확인
		if (ChronoUnit.MONTHS.between(createdAt, now) <= 1) {
			throw ReviewProductUsageTimeNotReachedException()
		}
		return review
	}
	
	suspend fun validateDeleteReview(request: ReviewDeleteRequest): Review {
		val review = reviewRepository.findByPublicId(request.reviewId)
		
		val createdAt = review.getCreatedAt()
		val now = LocalDateTime.now()
		// 리뷰 생성 후 일주일이 지났는지 확인
		if (ChronoUnit.DAYS.between(createdAt, now) > 7) {
			throw ReviewDeletionTimeLimitExceededException()
		}
		return review
	}
	
	suspend fun validateReportUser(request: ReviewReportRequest): Account {
		val account = accountRepository.findAccountByUserId(request.userId) ?: throw AccountNotFoundException()
		val reportLogs = account.getReviewReportLogs()
		
		// 리뷰 신고 정지가 7번 이상이면 계정 정지
		if (account.getReviewReportBanLogs().size > 7) {
			// TODO: 정지 코드
			accountRepository.save(account)
			throw ReviewPolicyViolationException()
		}
		
		// 7일 동안 최대 5회 신고
		val reportThreshold = 5
		val evaluationPeriod = Duration.ofDays(7)
		
		val recentReports = reportLogs.filter {
			it.isAfter(LocalDateTime.now().minus(evaluationPeriod))
		}
		
		if (recentReports.size > reportThreshold) {
			account.addReviewReportBanLog(LocalDateTime.now())
			accountRepository.save(account)
			throw ReviewReportAbuseBlockedException()
		}
		
		return account
	}
}