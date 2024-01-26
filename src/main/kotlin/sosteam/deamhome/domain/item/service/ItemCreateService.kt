package sosteam.deamhome.domain.item.service

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.global.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.item.handler.request.ItemRequest
import sosteam.deamhome.domain.item.handler.response.ItemResponse
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.global.image.provider.ImageProvider

@Service
@Transactional
class ItemCreateService (
    private val itemRepository: ItemRepository,
    private val itemCategoryRepository: ItemCategoryRepository,
    private val imageProvider: ImageProvider,
){
    suspend fun createItem(request: ItemRequest): ItemResponse {
        // 카테고리가 존재하는지 확인
        itemCategoryRepository.findByPublicId(request.categoryPublicId)
            ?: throw CategoryNotFoundException()

        // TODO sellerId 가 존재하는지 확인?

        val item = request.asDomain().apply {
            // 이미지 저장
            imageUrls = request.images.map {
                imageProvider.saveImage(it, "item", publicId).awaitSingle().fileUrl
            }.toMutableList()
        }

        val saveItem = itemRepository.save(item)
        return ItemResponse.fromItem(item)
    }
}