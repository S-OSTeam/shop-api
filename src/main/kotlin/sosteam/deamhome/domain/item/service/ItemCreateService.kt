package sosteam.deamhome.domain.item.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.item.handler.request.ItemRequest
import sosteam.deamhome.domain.item.handler.response.ItemResponse
import sosteam.deamhome.domain.item.repository.ItemCategoryRepository
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.global.category.exception.CategoryNotFoundException
import sosteam.deamhome.global.image.provider.ImageProvider

@Service
@Transactional
class ItemCreateService(
	private val itemRepository: ItemRepository,
	private val itemCategoryRepository: ItemCategoryRepository,
	private val imageProvider: ImageProvider,
) {
	suspend fun createItem(request: ItemRequest): ItemResponse {
		// 카테고리가 존재하는지 확인
		itemCategoryRepository.findByPublicId(request.categoryPublicId)
			?: throw CategoryNotFoundException()
		
		// TODO storeId 가 존재하는지 확인?
		
		val item = request.asDomain()
		val saveItem = itemRepository.save(item)
		return ItemResponse.fromItem(saveItem)
	}
}