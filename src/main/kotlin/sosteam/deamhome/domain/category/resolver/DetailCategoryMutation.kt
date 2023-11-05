package sosteam.deamhome.domain.category.resolver

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.category.dto.ItemCategoryDTO
import sosteam.deamhome.domain.category.dto.request.DetailCategoryCreateRequest
import sosteam.deamhome.domain.category.service.DeatilCategoryCreateService

@RestController
class DetailCategoryMutation (
    private val detailCategoryCreateService: DeatilCategoryCreateService
) {
    @MutationMapping
    suspend fun createDetailCategory(@Argument request: DetailCategoryCreateRequest) : ItemCategoryDTO {
        return detailCategoryCreateService.createDetailCategory(request)
    }

}