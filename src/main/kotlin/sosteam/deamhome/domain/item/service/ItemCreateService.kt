package sosteam.deamhome.domain.item.service

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.handler.request.ItemRequest
import sosteam.deamhome.domain.item.handler.response.ItemResponse
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.global.image.provider.ImageProvider
import sosteam.deamhome.global.sequence.provider.SequenceGenerator

@Service
@Transactional
class ItemCreateService (
    private val itemRepository: ItemRepository,
    private val itemCategoryRepository: ItemCategoryRepository,
    private val imageProvider: ImageProvider,
    private val sequenceGenerator: SequenceGenerator,
    @Value("\${item.sequence.name}")
    private val sequenceName: String,
){
    suspend fun createItem(request: ItemRequest): ItemResponse {
        // 카테고리가 존재하는지 확인
        val itemCategory = itemCategoryRepository.findByPublicId(request.categoryPublicId)
            ?: throw CategoryNotFoundException()

        // TODO sellerId 가 존재하는지 확인?

        val item = request.asDomain().apply {
            publicId = sequenceGenerator.generateSequence(sequenceName)
            // 이미지 저장
            images = request.images.map {
                imageProvider.saveImage(it, "item", id).awaitSingle()
            }.toMutableList()
        }

        val saveItem = itemRepository.save(item).awaitSingle()
        return ItemResponse.fromItem(item)
    }
}