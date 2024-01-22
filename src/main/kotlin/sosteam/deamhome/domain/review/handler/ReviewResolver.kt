package sosteam.deamhome.domain.review.handler

import jakarta.validation.Valid
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.review.handler.ReviewSearchType.*
import sosteam.deamhome.domain.review.handler.request.ReviewCreateRequest
import sosteam.deamhome.domain.review.handler.request.ReviewDeleteRequest
import sosteam.deamhome.domain.review.handler.request.ReviewSearchRequest
import sosteam.deamhome.domain.review.handler.request.ReviewUpdateRequest
import sosteam.deamhome.domain.review.handler.response.ReviewResponse
import sosteam.deamhome.domain.review.service.ReviewCreateService
import sosteam.deamhome.domain.review.service.ReviewDeleteService
import sosteam.deamhome.domain.review.service.ReviewSearchService
import sosteam.deamhome.domain.review.service.ReviewUpdateService

@RestController
class ReviewResolver(
	private val reviewCreateService: ReviewCreateService,
	private val reviewSearchService: ReviewSearchService,
	private val reviewUpdateService: ReviewUpdateService,
	private val reviewDeleteService: ReviewDeleteService
) {
	@MutationMapping
	suspend fun createReview(@Argument @Valid request: ReviewCreateRequest): ReviewResponse {
		return reviewCreateService.createReview(request)
	}
	
	@QueryMapping
	suspend fun findReviews(
		@Argument @Valid request: ReviewSearchRequest,
		@Argument searchType: ReviewSearchType
	): List<ReviewResponse> {
		return when (searchType) {
			USER_AND_ITEM_ID -> reviewSearchService.searchReviewByUserAndItemId(request)
			REVIEW_ID -> listOf(reviewSearchService.searchReviewByReviewId(request))
			USER_ID -> reviewSearchService.searchReviewsByUserId(request)
			ITEM_ID -> reviewSearchService.searchReviewsByItemId(request)
		}
	}
	
	@MutationMapping
	suspend fun updateReview(@Argument @Valid request: ReviewUpdateRequest): ReviewResponse {
		return reviewUpdateService.updateReview(request)
	}
	
	@MutationMapping
	suspend fun deleteReview(@Argument @Valid request: ReviewDeleteRequest): ResponseEntity<String> {
		return reviewDeleteService.deleteReview(request)
	}
}