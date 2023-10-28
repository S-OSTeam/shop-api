package sosteam.deamhome.domain.category.service

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.category.DTO.ItemCategoryDTO
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.entity.ItemDetailCategory
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.category.resolver.request.DetailCategoryCreateRequest

@Service
class DeatilCategoryCreateService(
    private val itemCategoryRepository: ItemCategoryRepository
) {
    suspend fun createDetailCategory(request: DetailCategoryCreateRequest) : ItemCategoryDTO{
        val itemCategory = itemCategoryRepository.findByTitle(request.categoryTitle) ?: ItemCategory(title = request.categoryTitle)
        val itemDetailCategories = itemCategory.itemDetailCategories

        val existingItemDetailCategory = itemDetailCategories.find { it.title == request.title }

        if (existingItemDetailCategory == null) {
            itemDetailCategories.add(ItemDetailCategory(title = request.title))
            itemCategory.modifyDetailCategory(itemDetailCategories)
        }

        val inserted = itemCategoryRepository.save(itemCategory).awaitSingleOrNull()
        return ItemCategoryDTO(
            title = inserted?.title
        )
    }
}