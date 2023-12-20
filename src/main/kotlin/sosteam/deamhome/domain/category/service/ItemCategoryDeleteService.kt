package sosteam.deamhome.domain.category.service

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository

@Service
@Transactional
class ItemCategoryDeleteService (
    private val itemCategoryRepository: ItemCategoryRepository
) {
    suspend fun deleteItemCategoryBySequence(sequence: Long): String {
        val itemCategory = (itemCategoryRepository.deleteBySequence(sequence)
            ?: throw CategoryNotFoundException())

        itemCategory.parentSeq?.let { parentSeq ->
            val parentCategory = itemCategoryRepository.findBySequence(parentSeq)
                ?: throw CategoryNotFoundException(message = "상위 카테고리를 찾을 수 없습니다.")

            parentCategory.childrenSeq.remove(sequence)
            itemCategoryRepository.save(parentCategory).awaitSingleOrNull()
        }
        return itemCategory.title
    }

}
