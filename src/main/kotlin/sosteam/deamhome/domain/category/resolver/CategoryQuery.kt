package sosteam.deamhome.domain.category.resolver

import kotlinx.coroutines.reactor.asFlux
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import sosteam.deamhome.domain.category.dto.ItemCategoryDTO2
import sosteam.deamhome.domain.category.dto.response.ItemDetailCategoryResponse
import sosteam.deamhome.domain.category.service.CategorySearchService
import sosteam.deamhome.domain.category.service.DetailCategorySearchService
import sosteam.deamhome.domain.item.entity.dto.ItemDTO

@RestController
class CategoryQuery (
    private val categorySearchService: CategorySearchService,
    private val detailCategorySearchService: DetailCategorySearchService
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
    suspend fun getItemDetailCategoryByTitle(@Argument title: String): ItemDetailCategoryResponse{
        //없으면 어떡하지? 에러처리 백랜딩에서 어떻게 했더라..
        //근데 response 에다가 id 넣어도 되나..?
        //detail mutation 따로 파기
        return detailCategorySearchService.getItemDetailCategoryByTitle(title)
    }
//    @QueryMapping
//    fun getItemsContainsTitle(@Argument title:String): Flux<ItemDTO> {
//        val itemsContainsTitle = categorySearchService.getItemsContainsTitle(title)
//            .asFlux()
//        return itemsContainsTitle
//    }

}