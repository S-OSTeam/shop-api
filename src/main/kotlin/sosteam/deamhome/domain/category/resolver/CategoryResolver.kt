package sosteam.deamhome.domain.category.resolver

import kotlinx.coroutines.flow.toList
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.category.dto.request.CategoryRequestDTO
import sosteam.deamhome.domain.category.dto.response.ItemCategoryResponseDTO
import sosteam.deamhome.domain.category.service.CategoryCreateService
import sosteam.deamhome.domain.category.service.CategoryDeleteService
import sosteam.deamhome.domain.category.service.CategoryUpdateService
import sosteam.deamhome.domain.category.service.CategoryValidService

@RestController
class CategoryResolver(
    private val categoryCreateService: CategoryCreateService,
    private val categoryValidService: CategoryValidService,
    private val categoryDeleteService: CategoryDeleteService,
    private val categoryUpdateService: CategoryUpdateService
) {
    @MutationMapping
    suspend fun createCategory(@Argument request: CategoryRequestDTO) : ItemCategoryResponseDTO {
        categoryValidService.isAlreadyExistCategory(request.title)
        return categoryCreateService.createCategory(request)
    }

    @MutationMapping
    suspend fun deleteCategory(@Argument id: String) : Boolean{
        return categoryDeleteService.deleteItemCategoryById(id) == 1L
    }

    @QueryMapping
    suspend fun findCategoryByTitle(@Argument title:String) : ItemCategoryResponseDTO {
        return categoryValidService.isExistCategory(title)
    }

    @QueryMapping
    suspend fun getItemCategoriesContainsTitle(@Argument title:String): List<ItemCategoryResponseDTO> {
        return categoryValidService.getItemCategoriesContainsTitle(title).toList()
    }

    @MutationMapping
    suspend fun moveDetailCategory(@Argument detailCategoryTitle: String, @Argument categoryTitle: String)
    : ItemCategoryResponseDTO {
        return categoryUpdateService.modifyDetailCategory(detailCategoryTitle, categoryTitle)
    }

}