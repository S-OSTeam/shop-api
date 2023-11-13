package sosteam.deamhome.domain.category.resolver

import kotlinx.coroutines.reactor.asFlux
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import sosteam.deamhome.domain.category.dto.response.ItemCategoryResponseDTO
import sosteam.deamhome.domain.category.service.CategoryValidService

@RestController
class CategoryQuery (
    private val categoryValidService: CategoryValidService
) {

    @QueryMapping
    suspend fun findCategoryByTitle(@Argument title:String) : ItemCategoryResponseDTO {
        return categoryValidService.isExistCategory(title)
    }

    @QueryMapping
    fun getItemCategoriesContainsTitle(@Argument title:String): Flux<ItemCategoryResponseDTO> {
        return categoryValidService.getItemCategoriesContainsTitle(title).asFlux()
    }

}