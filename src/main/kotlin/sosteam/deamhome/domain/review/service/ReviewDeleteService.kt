package sosteam.deamhome.domain.review.service

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.review.exception.ReviewNotFoundException
import sosteam.deamhome.domain.review.handler.request.ReviewDeleteRequest
import sosteam.deamhome.domain.review.repository.ReviewRepository
import sosteam.deamhome.global.image.provider.ImageProvider

@Service
class ReviewDeleteService(
	private val reviewRepository: ReviewRepository,
	private val imageProvider: ImageProvider
) {
	suspend fun deleteReview(request: ReviewDeleteRequest): ResponseEntity<String> {
		val review = reviewRepository.findById(request.reviewId).awaitSingleOrNull() ?: throw ReviewNotFoundException()
		review.images.forEach {
			imageProvider.deleteImage(it.fileUrl)
		}
		reviewRepository.deleteById(request.reviewId).awaitSingleOrNull()
		return ResponseEntity.ok("Review 제거 성공 : `${request.reviewId}` ")
	}
}