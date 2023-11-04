package sosteam.deamhome.domain.category.service

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.category.dto.ItemCategoryDTO

import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.category.resolver.request.CategoryCreateRequest

@Service
@Transactional
class CategoryCreateService(
    private val itemCategoryRepository: ItemCategoryRepository
) {

    //TODO dto 뭘로 바꾸지? 지금은 findby 해서 이미 있으면 save 로 안만들어지게 해놓음
    suspend fun createCategory(request: CategoryCreateRequest) : ItemCategoryDTO {
        val itemCategory = itemCategoryRepository.findByTitle(request.title) ?: ItemCategory(title = request.title)
        val inserted = itemCategoryRepository.save(itemCategory).awaitSingleOrNull()

        return ItemCategoryDTO(
            title = inserted?.title
        )
    }
}