package sosteam.deamhome.domain.category.service

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.category.dto.request.ItemCategoryRequest
import sosteam.deamhome.domain.category.dto.response.ItemCategoryResponse
import sosteam.deamhome.domain.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.exception.CategorySaveFailException
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
            sequence = sequenceGenerator.generateSequence(ItemCategory.SEQUENCE_NAME)
        }

        itemCategory.parentSeq?.let { parentSeq ->
            val parentCategory = itemCategoryRepository.findBySequence(parentSeq)
                ?: throw CategoryNotFoundException(message = "상위 카테고리를 찾을 수 없습니다.")

            updateParent(parentCategory, itemCategory.sequence)

            val depth = calcDepth(parentCategory) + 1
            if (depth > ItemCategory.MAX_DEPTH) {
                throw MaxDepthExceedException()
            }
        }

        val saveCategory = itemCategoryRepository.save(itemCategory).awaitSingleOrNull()
            ?: throw CategorySaveFailException()

        return ItemCategoryResponse.fromItemCategory(saveCategory)
    }

    private suspend fun calcDepth(category: ItemCategory): Int {
        return if (category.parentSeq == null) {
            0
        } else {
            val parentCategory = itemCategoryRepository.findBySequence(category.parentSeq!!)
                ?: throw CategoryNotFoundException(message = "상위 카테고리를 찾을 수 없습니다.")
            calcDepth(parentCategory) + 1
        }
    }

    private suspend fun updateParent(parentCategory: ItemCategory, childSequence: Long) {
        parentCategory.childrenSeq.add(childSequence)
        itemCategoryRepository.save(parentCategory).awaitSingleOrNull()
    }
}
