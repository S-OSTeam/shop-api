package sosteam.deamhome.domain.review.service

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.review.entity.Review
import sosteam.deamhome.domain.review.handler.request.ReviewDeleteRequest
import sosteam.deamhome.domain.review.repository.ReviewRepository
import sosteam.deamhome.global.image.provider.ImageProvider

@Service
@Transactional
class ReviewDeleteService(
	private val reviewRepository: ReviewRepository,
	private val imageProvider: ImageProvider
) {
	suspend fun deleteReview(request: ReviewDeleteRequest): ResponseEntity<String> {
		val review = reviewRepository.findById(request.reviewId) as Review
		for (image in review.images) {
			imageProvider.deleteImage(image.path)
		}
		reviewRepository.delete(review)
		return ResponseEntity.ok("Review 제거 성공 : `${review.title}` ")
	}
}