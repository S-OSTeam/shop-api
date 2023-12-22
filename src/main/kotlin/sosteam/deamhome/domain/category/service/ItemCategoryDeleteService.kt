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
    suspend fun deleteItemCategoryByPublicId(publicId: Long): String {
        val itemCategory = (itemCategoryRepository.deleteByPublicId(publicId)
            ?: throw CategoryNotFoundException())

        itemCategory.parentPublicId?.let { parentPublicId ->
            val parentCategory = itemCategoryRepository.findByPublicId(parentPublicId)
                ?: throw CategoryNotFoundException(message = "상위 카테고리를 찾을 수 없습니다.")

            parentCategory.childrenPublicId.remove(publicId)
            itemCategoryRepository.save(parentCategory).awaitSingleOrNull()
        }
        return itemCategory.title
    }

}
