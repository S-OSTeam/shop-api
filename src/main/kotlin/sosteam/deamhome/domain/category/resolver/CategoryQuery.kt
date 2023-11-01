package sosteam.deamhome.domain.category.resolver

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactor.asFlux
import kotlinx.coroutines.reactor.flux
import kotlinx.coroutines.reactor.mono
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import sosteam.deamhome.domain.category.dto.ItemCategoryDTO2
import sosteam.deamhome.domain.category.service.CategorySearchService
import java.util.concurrent.CompletableFuture

@RestController
class CategoryQuery (
    private val categorySearchService: CategorySearchService
) {

    @QueryMapping
    suspend fun findCategoryByTitle(@Argument title:String) : ItemCategoryDTO2 {
        return categorySearchService.findCategoryByTitle(title)
    }

    @QueryMapping
    fun getItemsContainsTitle(@Argument title:String): Flow<ItemCategoryDTO2> {
        val itemsContainsTitle = categorySearchService.getItemsContainsTitle(title)
//            .asFlux()
        return itemsContainsTitle
    }
}