package sosteam.deamhome.domain.category.resolver

import kotlinx.coroutines.reactor.asFlux
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import sosteam.deamhome.domain.category.dto.ItemCategoryDTO2
import sosteam.deamhome.domain.category.service.CategorySearchService
import sosteam.deamhome.domain.item.entity.dto.ItemDTO

@RestController
class CategoryQuery (
    private val categorySearchService: CategorySearchService
) {

    @QueryMapping
    suspend fun findCategoryByTitle(@Argument title:String) : ItemCategoryDTO2 {
        return categorySearchService.findCategoryByTitle(title)
    }

    @QueryMapping
    fun getItemCategoriesContainsTitle(@Argument title:String): Flux<ItemCategoryDTO2> {
        val itemCategoriesContainsTitle = categorySearchService.getItemCategoriesContainsTitle(title)
            .asFlux()
        return itemCategoriesContainsTitle
    }

    @QueryMapping
    fun getItemsContainsTitle(@Argument title:String): Flux<ItemDTO> {
        val itemsContainsTitle = categorySearchService.getItemsContainsTitle(title)
            .asFlux()
        return itemsContainsTitle
    }

}