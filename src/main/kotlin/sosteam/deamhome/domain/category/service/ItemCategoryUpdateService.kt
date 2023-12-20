package sosteam.deamhome.domain.category.service

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.exception.MaxDepthExceedException
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository

@Service
@Transactional
class ItemCategoryUpdateService (
    private val itemCategoryRepository: ItemCategoryRepository,
) {
    suspend fun moveCategory(sourceSeq: Long, destinationSeq: Long) {
        val sourceCategory = (itemCategoryRepository.findBySequence(sourceSeq)
            ?: throw CategoryNotFoundException())

        sourceCategory.parentSeq?.let { parentSeq ->
            val parentCategory = itemCategoryRepository.findBySequence(parentSeq)
                ?: throw CategoryNotFoundException(message = "상위 카테고리를 찾을 수 없습니다.")

            parentCategory.childrenSeq.remove(sourceSeq)
            itemCategoryRepository.save(parentCategory).awaitSingleOrNull()
        }

        val destinationCategory = (itemCategoryRepository.findBySequence(destinationSeq)
            ?: throw CategoryNotFoundException(message = "이동할 카테고리를 찾을 수 없습니다."))

        val depth = calcDepth(destinationCategory) + 1
        if (depth > ItemCategory.MAX_DEPTH) {
            throw MaxDepthExceedException()
        }

        sourceCategory.parentSeq = destinationSeq
        itemCategoryRepository.save(sourceCategory).awaitSingleOrNull()

        destinationCategory.childrenSeq.add(sourceSeq)
        itemCategoryRepository.save(destinationCategory).awaitSingleOrNull()
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

}