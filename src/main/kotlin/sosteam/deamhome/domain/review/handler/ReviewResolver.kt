package sosteam.deamhome.domain.review.handler

import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.review.handler.request.ReviewCreateRequest
import sosteam.deamhome.domain.review.handler.request.ReviewDeleteRequest
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
	private val reviewDeleteService: ReviewDeleteService,
) {
	@PostMapping("/review")
	suspend fun createReview(request: ReviewCreateRequest): ReviewResponse {
		return reviewCreateService.createReview(request)
	}
	
	// TODO: createReviewImages (이미지는 rest로)
	
	//	@QueryMapping
//	suspend fun searchReviewByUserId():{
//		// TODO: 유저의 리뷰들 모아보기
//	}
//
//	@QueryMapping
//	suspend fun searchReviewByItem():{
//		// TODO: 해당 상품의 리뷰 모아보기
//	}
//
//	@MutationMapping
//	suspend fun updateReview():{
//
//	}
//
	@MutationMapping
	suspend fun deleteReview(request: ReviewDeleteRequest): ResponseEntity<String> {
		return reviewDeleteService.deleteReview(request)
	}
//
//	@MutationMapping
//	suspend fun updateReviewLike():{
//
//	}
}