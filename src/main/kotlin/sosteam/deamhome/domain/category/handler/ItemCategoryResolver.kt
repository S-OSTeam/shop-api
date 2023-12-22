package sosteam.deamhome.domain.category.handler

import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import kotlinx.coroutines.flow.toList
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
//import sosteam.deamhome.domain.category.handler.request.ItemCategoryListRequest
import sosteam.deamhome.domain.category.handler.request.ItemCategoryRequest
import sosteam.deamhome.domain.category.handler.response.ItemCategoryResponse
import sosteam.deamhome.domain.category.service.ItemCategoryCreateService
import sosteam.deamhome.domain.category.service.ItemCategoryDeleteService
import sosteam.deamhome.domain.category.service.ItemCategorySearchService
import sosteam.deamhome.domain.category.service.ItemCategoryUpdateService

@RestController
class ItemCategoryResolver(
    private val itemCategoryCreateService: ItemCategoryCreateService,
    private val itemCategorySearchService: ItemCategorySearchService,
    private val itemCategoryDeleteService: ItemCategoryDeleteService,
    private val itemCategoryUpdateService: ItemCategoryUpdateService
) {
    @MutationMapping
    suspend fun createItemCategory(@Argument @Valid request: ItemCategoryRequest) : ItemCategoryResponse {
        return itemCategoryCreateService.createCategory(request)
    }

    @QueryMapping
    suspend fun getItemCategoryByPublicId(@Argument @Min(0L) publicId: Long) : ItemCategoryResponse {
        return itemCategorySearchService.getItemCategoryByPublicId(publicId)
    }

    @QueryMapping
    suspend fun findItemCategoriesContainsTitle(@Argument @NotBlank title: String) : List<ItemCategoryResponse> {
        return itemCategorySearchService.findItemCategoriesContainsTitle(title).toList()
    }

    @MutationMapping
    suspend fun deleteItemCategoryByPublicId(@Argument @Min(0L) publicId: Long) : String{
        val deletedTitle = itemCategoryDeleteService.deleteItemCategoryByPublicId(publicId)
        return "$deletedTitle has been deleted"
    }

    @QueryMapping
    suspend fun findAllItemCategories() : List<ItemCategoryResponse> {
        return itemCategorySearchService.findAllItemCategories().toList()
    }

//    @MutationMapping
//    suspend fun updateItemCategory(@Argument itemCategoryListRequest: ItemCategoryListRequest) : String {
//        return itemCategoryUpdateService.updateItemCategory(itemCategoryListRequest)
//        return "updateItemCategory"
//    }

}