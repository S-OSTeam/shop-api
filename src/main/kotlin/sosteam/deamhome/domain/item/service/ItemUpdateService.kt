package sosteam.deamhome.domain.item.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.item.exception.ItemNotFoundException
import sosteam.deamhome.domain.item.handler.request.ItemUpdateRequest
import sosteam.deamhome.domain.item.handler.response.ItemResponse
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.global.category.exception.CategoryNotFoundException
import sosteam.deamhome.global.image.provider.ImageProvider

@Service
@Transactional
class ItemUpdateService(
	private val itemRepository: ItemRepository,
	private val itemCategoryRepository: ItemCategoryRepository,
	private val imageProvider: ImageProvider
) {

	suspend fun updateItem(request: ItemUpdateRequest): ItemResponse {
		// 기존 아이템을 가져옵니다
		val existingItem = itemRepository.findByPublicId(request.publicId)
			?: throw ItemNotFoundException()

		// request 에서 null 이 아닌 값만 사용하여 새 객체 생성
		val updatedItem = existingItem.copy(
			categoryPublicId = request.categoryPublicId?.let { categoryId ->
				itemCategoryRepository.findByPublicId(categoryId)
					?: throw CategoryNotFoundException()
				categoryId
			} ?: existingItem.categoryPublicId,
			title = request.title ?: existingItem.title,
			content = request.content ?: existingItem.content,
			summary = request.summary ?: existingItem.summary,
			price = request.price ?: existingItem.price,
			status = request.status ?: existingItem.status,
			sellerId = request.sellerId ?: existingItem.sellerId,
			freeDelivery = request.freeDelivery ?: existingItem.freeDelivery,
			option = request.option ?: existingItem.option,
			productNumber = request.productNumber ?: existingItem.productNumber,
			deadline = request.deadline ?: existingItem.deadline,
			originalWork = request.originalWork ?: existingItem.originalWork,
			material = request.material ?: existingItem.material,
			size = request.size ?: existingItem.size,
			weight = request.weight ?: existingItem.weight,
			shippingCost = request.shippingCost ?: existingItem.shippingCost,
		).apply {
			// 새 이미지가 있는 경우에만 기존 이미지를 삭제하고 새 이미지를 저장
			imageUrls = if (request.images != null) {
				// 이미지 삭제
				existingItem.imageUrls.forEach { imageUrl ->
					imageProvider.deleteImage(imageUrl)
				}
				// 새 이미지 저장
				request.images!!.map { imageRequest ->
					imageProvider.saveImage(imageRequest.image, imageRequest.outer, imageRequest.inner, imageRequest.resizeWidth, imageRequest.resizeHeight).fileUrl
				}
			} else {
				// 새 이미지가 없는 경우에는 기존 이미지를 그대로 사용
				existingItem.imageUrls
			}
		}

		val savedItem = itemRepository.save(updatedItem)
		return ItemResponse.fromItem(savedItem)
	}
}