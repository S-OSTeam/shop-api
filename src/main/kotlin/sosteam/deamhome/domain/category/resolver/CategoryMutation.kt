package sosteam.deamhome.domain.category.resolver

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.category.dto.request.CategoryRequestDTO
import sosteam.deamhome.domain.category.dto.response.ItemCategoryResponseDTO
import sosteam.deamhome.domain.category.service.CategoryCreateService
import sosteam.deamhome.domain.category.service.CategoryDeleteService
import sosteam.deamhome.domain.category.service.CategoryValidService

@RestController
class CategoryMutation(
    private val categoryCreateService: CategoryCreateService,
    private val categoryValidService: CategoryValidService,
    private val categoryDeleteService: CategoryDeleteService
) {
    @MutationMapping
    suspend fun createCategory(@Argument request: CategoryRequestDTO) : ItemCategoryResponseDTO {
        categoryValidService.isAlreadyExistCategory(request.title)
        return categoryCreateService.createCategory(request)
    }

    @MutationMapping
    suspend fun deleteCategory(@Argument id: String){
        categoryDeleteService.deleteItemCategoryById(id)
    }

}