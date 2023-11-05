package sosteam.deamhome.domain.category.resolver

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.category.dto.ItemCategoryDTO
import sosteam.deamhome.domain.category.dto.request.CategoryCreateRequest
import sosteam.deamhome.domain.category.service.CategoryCreateService

@RestController
class CategoryMutation(
    private val categoryCreateService: CategoryCreateService
) {
    @MutationMapping
    suspend fun createCategory(@Argument request: CategoryCreateRequest) : ItemCategoryDTO {
        return categoryCreateService.createCategory(request)
    }

}