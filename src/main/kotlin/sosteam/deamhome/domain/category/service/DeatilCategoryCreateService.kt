package sosteam.deamhome.domain.category.service

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.category.dto.ItemCategoryDTO
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.entity.ItemDetailCategory
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.category.dto.request.DetailCategoryCreateRequest

@Service
class DeatilCategoryCreateService(
    private val itemCategoryRepository: ItemCategoryRepository
) {
    //TODO dto 뭘로 바꾸지? 지금은 findby 해서 이미 있으면 save 로 안만들어지게 해놓음
    suspend fun createDetailCategory(request: DetailCategoryCreateRequest) : ItemCategoryDTO {
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