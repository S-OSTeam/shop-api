package sosteam.deamhome.domain.category.service

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.category.DTO.ItemCategoryDTO
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.category.resolver.request.CategoryCreateRequest

@Service
class CategoryCreateService(
    private val itemCategoryRepository: ItemCategoryRepository
) {

    suspend fun createCategory(request: CategoryCreateRequest) : ItemCategoryDTO {
        val itemCategory = itemCategoryRepository.findByTitle(request.title) ?: ItemCategory(title = request.title)
        val inserted = itemCategoryRepository.save(itemCategory).awaitSingleOrNull()

        return ItemCategoryDTO(
            title = inserted?.title
        )
    }
}