package sosteam.deamhome.domain.category.service

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.category.dto.ItemCategoryDTO

import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.category.dto.request.CategoryCreateRequest

@Service
class CategoryCreateService(
    private val itemCategoryRepository: ItemCategoryRepository
) {

    //TODO dto 뭘로 바꾸지? 지금은 findby 해서 이미 있으면 save 로 안만들어지게 해놓음
    //TODO valid service 로 있는지 검증 후 없으면 컨트롤러단에서 에러 처리함
    suspend fun createCategory(request: CategoryCreateRequest) : ItemCategoryDTO {
        val itemCategory = itemCategoryRepository.findByTitle(request.title) ?: ItemCategory(title = request.title)
        val inserted = itemCategoryRepository.save(itemCategory).awaitSingleOrNull()

        return ItemCategoryDTO(
            title = inserted?.title
        )
    }
}