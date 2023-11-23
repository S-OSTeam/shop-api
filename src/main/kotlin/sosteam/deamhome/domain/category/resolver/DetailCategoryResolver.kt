package sosteam.deamhome.domain.category.resolver

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.category.dto.request.DetailCategoryRequestDTO
import sosteam.deamhome.domain.category.dto.response.ItemCategoryResponseDTO
import sosteam.deamhome.domain.category.dto.response.ItemDetailCategoryResponseDTO
import sosteam.deamhome.domain.category.service.DetailCategoryCreateService
import sosteam.deamhome.domain.category.service.DetailCategorySearchService

@RestController
class DetailCategoryResolver (
    private val detailCategoryCreateService: DetailCategoryCreateService,
    private val detailCategorySearchService: DetailCategorySearchService
) {
    @MutationMapping
    suspend fun createDetailCategory(@Argument request: DetailCategoryRequestDTO) : ItemCategoryResponseDTO {
        return detailCategoryCreateService.createDetailCategory(request)
    }

    @QueryMapping
    suspend fun getItemDetailCategoryByTitle(@Argument title: String): ItemDetailCategoryResponseDTO {
        return detailCategorySearchService.getItemDetailCategoryByTitle(title)
    }

}