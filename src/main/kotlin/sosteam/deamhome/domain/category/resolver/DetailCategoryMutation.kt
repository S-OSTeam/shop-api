package sosteam.deamhome.domain.category.resolver

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.category.dto.ItemCategoryDTO
import sosteam.deamhome.domain.category.dto.request.DetailCategoryCreateRequestDTO
import sosteam.deamhome.domain.category.dto.response.ItemCategoryResponseDTO
import sosteam.deamhome.domain.category.service.CategoryValidService
import sosteam.deamhome.domain.category.service.DeatilCategoryCreateService

@RestController
class DetailCategoryMutation (
    private val categoryValidService: CategoryValidService,
    private val detailCategoryCreateService: DeatilCategoryCreateService
) {
    @MutationMapping
    suspend fun createDetailCategory(@Argument request: DetailCategoryCreateRequestDTO) : ItemCategoryResponseDTO {
        return detailCategoryCreateService.createDetailCategory(request)
    }

}