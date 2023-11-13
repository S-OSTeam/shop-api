package sosteam.deamhome.domain.category.service

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.category.dto.request.CategoryCreateRequestDTO
import sosteam.deamhome.domain.category.dto.response.ItemCategoryResponseDTO
import sosteam.deamhome.domain.category.exception.CategorySaveFailException

@Service
class CategoryCreateService(
    private val itemCategoryRepository: ItemCategoryRepository
) {
    suspend fun createCategory(request: CategoryCreateRequestDTO): ItemCategoryResponseDTO {
        val itemCategory = ItemCategory(title = request.title)
        val insertedItemCategory = itemCategoryRepository.save(itemCategory).awaitSingleOrNull()
            ?: throw CategorySaveFailException()

        return ItemCategoryResponseDTO.fromItemCategory(insertedItemCategory)
    }

}