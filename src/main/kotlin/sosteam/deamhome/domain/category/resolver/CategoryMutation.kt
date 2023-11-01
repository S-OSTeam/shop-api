package sosteam.deamhome.domain.category.resolver

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.category.dto.ItemCategoryDTO
import sosteam.deamhome.domain.category.resolver.request.CategoryCreateRequest
import sosteam.deamhome.domain.category.resolver.request.DetailCategoryCreateRequest
import sosteam.deamhome.domain.category.service.CategoryCreateService
import sosteam.deamhome.domain.category.service.DeatilCategoryCreateService

@RestController
class CategoryMutation(
    private val categoryCreateService: CategoryCreateService,
    private val detailCategoryCreateService: DeatilCategoryCreateService
) {
    @MutationMapping
    suspend fun createCategory(@Argument request: CategoryCreateRequest) : ItemCategoryDTO {
        return categoryCreateService.createCategory(request)
    }

    @MutationMapping
    suspend fun createDetailCategory(@Argument request: DetailCategoryCreateRequest) : ItemCategoryDTO {
        return detailCategoryCreateService.createDetailCategory(request)
    }
}