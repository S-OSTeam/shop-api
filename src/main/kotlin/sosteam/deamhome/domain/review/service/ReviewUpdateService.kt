package sosteam.deamhome.domain.review.service

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.item.exception.ItemNotFoundException
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.domain.review.exception.ReviewNotFoundException
import sosteam.deamhome.domain.review.handler.request.ReviewUpdateRequest
import sosteam.deamhome.domain.review.handler.response.ReviewResponse
import sosteam.deamhome.domain.review.repository.ReviewRepository
import sosteam.deamhome.global.image.provider.ImageProvider

@Service
class ReviewUpdateService(
	private val reviewRepository: ReviewRepository,
	private val itemRepository: ItemRepository,
	private val imageProvider: ImageProvider
) {
	suspend fun updateReview(request: ReviewUpdateRequest): ReviewResponse {
		val originReview = reviewRepository.findById(request.reviewId).awaitSingle() ?: throw ReviewNotFoundException()
		
		val updatedImage = originReview.images
		// DB에 있는 Images와 비교해서 없으면 제거
		updatedImage.filter { image ->
			!request.originImages.contains(image.fileUrl)
		}.forEach { image ->
			imageProvider.deleteImage(image.fileUrl)
		}
		updatedImage.removeIf { image ->
			!request.originImages.contains(image.fileUrl)
		}
		// 추가된 Images
		val addImages = request.addImages.map { imageProvider.saveImage(it, "review", "").awaitSingle() }
		updatedImage.addAll(addImages)
		
		val updatedReview = originReview.apply {
			title = request.title
			content = request.content
			score = request.score
			status = request.status
			images = updatedImage
			likeUsers = request.likeUsers.toMutableList()
			purchaseOptions = request.purchaseOptions.toMutableList()
		}
		
		val item = itemRepository.findItemById(updatedReview.itemId) ?: throw ItemNotFoundException()
		if (request.score != originReview.score) {
			item.avgReview = (item.avgReview * item.reviewCnt - originReview.score + request.score) / item.reviewCnt
			itemRepository.save(item).awaitSingle()
		}
		
		reviewRepository.save(updatedReview).awaitSingle()
		
		return ReviewResponse.fromReview(updatedReview)
	}
}