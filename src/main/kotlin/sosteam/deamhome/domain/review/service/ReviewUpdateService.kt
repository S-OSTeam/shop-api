package sosteam.deamhome.domain.review.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.item.exception.ItemNotFoundException
import sosteam.deamhome.domain.item.repository.ItemRepository
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
		val originReview = reviewRepository.findByPublicId(request.publicId)
		
		// DB에 있는 Images와 비교해서 없으면 제거
		request.beforeImageUrls.filter { image ->
			!request.afterImageUrls.contains(image)
		}.forEach { imageUrl ->
			imageProvider.deleteImageByUrl(imageUrl)
		}
		
		
		val updatedReview = originReview.apply {
			title = request.title
			content = request.content
			score = request.score
			status = request.status
			imageUrls = request.afterImageUrls.toMutableList()
			likeUsers = request.likeUsers.toMutableList()
			purchaseOptions = request.purchaseOptions.toMutableList()
		}
		
		val item = itemRepository.findByPublicId(updatedReview.itemId) ?: throw ItemNotFoundException()
		if (request.score != originReview.score) {
			item.avgReview = (item.avgReview * item.reviewCnt - originReview.score + request.score) / item.reviewCnt
			item.reviewScore[originReview.score - 1] -= 1
			item.reviewScore[request.score - 1] += 1
			itemRepository.save(item)
		}
		
		reviewRepository.save(updatedReview)
		
		return ReviewResponse.fromReview(updatedReview)
	}
}