package sosteam.deamhome.domain.category.resolver

import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import kotlinx.coroutines.flow.toList
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.category.dto.request.ItemCategoryRequest
import sosteam.deamhome.domain.category.dto.response.ItemCategoryResponse
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
    suspend fun getItemCategoryBySequence(@Argument @Min(0L) sequence: Long) : ItemCategoryResponse {
        return itemCategorySearchService.getItemCategoryBySequence(sequence)
    }

    @QueryMapping
    suspend fun findItemCategoriesContainsTitle(@Argument @NotBlank title: String) : List<ItemCategoryResponse> {
        return itemCategorySearchService.findItemCategoriesContainsTitle(title).toList()
    }

    @MutationMapping
    suspend fun deleteItemCategoryBySequence(@Argument @Min(0L) sequence: Long) : String{
        val deletedTitle = itemCategoryDeleteService.deleteItemCategoryBySequence(sequence)
        return "$deletedTitle has been deleted"
    }

    @MutationMapping
    suspend fun moveCategory(@Argument @Min(0L)sourceSeq: Long, @Argument @Min(0L)destinationSeq: Long) : String {
        itemCategoryUpdateService.moveCategory(sourceSeq, destinationSeq)
        return "moveCategory"
    }

}