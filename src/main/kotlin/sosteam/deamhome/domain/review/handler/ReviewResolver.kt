package sosteam.deamhome.domain.review.handler

import jakarta.validation.Valid
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.review.handler.request.*
import sosteam.deamhome.domain.review.handler.response.ReviewResponse
import sosteam.deamhome.domain.review.service.*

@RestController
class ReviewResolver(
	private val reviewCreateService: ReviewCreateService,
	private val reviewSearchService: ReviewSearchService,
	private val reviewUpdateService: ReviewUpdateService,
	private val reviewDeleteService: ReviewDeleteService,
	private val reviewLikeService: ReviewLikeService,
	private val reviewMonthService: ReviewMonthService,
	private val reviewValidService: ReviewValidService,
	private val reviewReportService: ReviewReportService
) {
	@MutationMapping
	suspend fun createReview(@Argument @Valid request: ReviewCreateRequest): ReviewResponse {
		return reviewCreateService.createReview(request)
	}
	
	@QueryMapping
	suspend fun findReviews(
		@Argument @Valid request: ReviewSearchRequest,
	): List<ReviewResponse> {
		return reviewSearchService.searchReviews(request)
	}
	
	@MutationMapping
	suspend fun updateReview(@Argument @Valid request: ReviewUpdateRequest): ReviewResponse {
		val review = reviewValidService.validateUpdateReview(request)
		return reviewUpdateService.updateReview(request, review)
	}
	
	@MutationMapping
	suspend fun deleteReview(@Argument @Valid request: ReviewDeleteRequest): ResponseEntity<String> {
		val review = reviewValidService.validateDeleteReview(request)
		return reviewDeleteService.deleteReview(request, review)
	}
	
	@MutationMapping
	suspend fun updateReviewLike(@Argument @Valid request: ReviewLikeRequest): ReviewResponse {
		return reviewLikeService.updateReviewLike(request)
	}
	
	@MutationMapping
	suspend fun updateMonthReview(@Argument @Valid request: ReviewMonthRequest): ReviewResponse {
		val review = reviewValidService.validateMonthReview(request)
		return reviewMonthService.updateMonthReview(request, review)
	}
	
	@MutationMapping
	suspend fun updateReviewReport(@Argument @Valid request: ReviewReportRequest): ReviewResponse {
		val account = reviewValidService.validateReportUser(request)
		return reviewReportService.updateReviewReport(request, account)
	}
	
	@MutationMapping
	suspend fun updateReviewLike(@Argument @Valid request: ReviewLikeRequest): ReviewResponse {
		return reviewLikeService.updateReviewLike(request)
	}
	
	@MutationMapping
	suspend fun updateMonthReview(@Argument @Valid request: ReviewMonthRequest): ReviewResponse {
		val review = reviewValidService.validateMonthReview(request)
		return reviewMonthService.updateMonthReview(request, review)
	}
	
	@MutationMapping
	suspend fun updateReviewReport(@Argument @Valid request: ReviewReportRequest): ReviewResponse {
		return reviewReportService.updateReviewReport(request)
	}
}