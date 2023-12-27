package sosteam.deamhome.domain.category.service

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.category.handler.request.ItemCategoryRequest
import sosteam.deamhome.domain.category.handler.response.ItemCategoryResponse
import sosteam.deamhome.domain.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.exception.MaxDepthExceedException
import sosteam.deamhome.global.sequence.provider.SequenceGenerator

@Service
@Transactional
class ItemCategoryCreateService(
    private val itemCategoryRepository: ItemCategoryRepository,
    private val sequenceGenerator: SequenceGenerator
) {

    suspend fun createCategory(request: ItemCategoryRequest): ItemCategoryResponse {
        val itemCategory = request.asDomain().apply {
            publicId = sequenceGenerator.generateSequence(ItemCategory.SEQUENCE_NAME)
        }

        itemCategory.parentPublicId?.let { parentPublicId ->
            val parentCategory = itemCategoryRepository.findByPublicId(parentPublicId)
                ?: throw CategoryNotFoundException(message = "상위 카테고리를 찾을 수 없습니다.")

            val depth = calcDepth(parentCategory) + 1
            if (depth > ItemCategory.MAX_DEPTH) {
                throw MaxDepthExceedException()
            }
        }

        val saveCategory = itemCategoryRepository.save(itemCategory).awaitSingle()

        return ItemCategoryResponse.fromItemCategory(saveCategory)
    }

    private suspend fun calcDepth(category: ItemCategory): Int {
        return if (category.parentPublicId == null) {
            0
        } else {
            val parentCategory = itemCategoryRepository.findByPublicId(category.parentPublicId!!)
                ?: throw CategoryNotFoundException(message = "상위 카테고리를 찾을 수 없습니다.")
            calcDepth(parentCategory) + 1
        }
    }

}
