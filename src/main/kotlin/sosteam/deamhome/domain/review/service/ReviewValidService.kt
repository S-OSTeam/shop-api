package sosteam.deamhome.domain.review.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.exception.AccountNotFoundException
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.review.entity.Review
import sosteam.deamhome.domain.review.exception.ReviewDeletionTimeLimitExceededException
import sosteam.deamhome.domain.review.exception.ReviewPolicyViolationException
import sosteam.deamhome.domain.review.exception.ReviewUpdateExpiredException
import sosteam.deamhome.domain.review.handler.request.ReviewDeleteRequest
import sosteam.deamhome.domain.review.handler.request.ReviewReportRequest
import sosteam.deamhome.domain.review.handler.request.ReviewUpdateRequest
import sosteam.deamhome.domain.review.repository.ReviewRepository
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

@Service
class ReviewValidService(
	private val reviewRepository: ReviewRepository,
	private val accountRepository: AccountRepository
) {
	suspend fun validateUpdateReview(request: ReviewUpdateRequest): Review {
		val review = reviewRepository.findByPublicId(request.publicId)
		
		val createdAt = review.getCreatedAt()
		val now = OffsetDateTime.now()
		
		// 리뷰 생성 후 일주일이 지났는지 확인
		if (ChronoUnit.DAYS.between(createdAt, now) > 7) {
			throw ReviewUpdateExpiredException()
		}
		return review
	}
	
	suspend fun validateDeleteReview(request: ReviewDeleteRequest): Review {
		val review = reviewRepository.findByPublicId(request.publicId)
		
		val createdAt = review.getCreatedAt()
		val now = OffsetDateTime.now()
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
		
		return account
	}
}