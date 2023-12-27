package sosteam.deamhome.domain.item.handler

import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import kotlinx.coroutines.flow.toList
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.item.handler.request.ItemRequest
import sosteam.deamhome.domain.item.handler.response.ItemResponse
import sosteam.deamhome.domain.item.service.ItemCreateService
import sosteam.deamhome.domain.item.service.ItemDeleteService
import sosteam.deamhome.domain.item.service.ItemSearchService


@RestController
class ItemResolver(
    private val itemCreateService: ItemCreateService,
    private val itemSearchService: ItemSearchService,
    private val itemDeleteService: ItemDeleteService
    ) {
    @MutationMapping
    suspend fun createItem(@Argument @Valid request: ItemRequest) : ItemResponse {
        return itemCreateService.createItem(request)
    }

    @QueryMapping
    suspend fun findItemsContainTitle(@Argument @NotBlank title: String): List<ItemResponse>{
        return itemSearchService.findItemsContainTitle(title).toList()
    }

    @QueryMapping
    suspend fun findItemsByCategoryTitle(@Argument @NotBlank categoryTitle: String): List<ItemResponse> {
        return itemSearchService.findItemsByCategoryTitle(categoryTitle)
    }

    @QueryMapping
    suspend fun findItemsByCategoryPublicId(@Argument @Min(1L) categoryPublicId: Long): List<ItemResponse> {
        return itemSearchService.findItemsByCategoryPublicId(categoryPublicId)
    }

    @QueryMapping
    suspend fun findItemByPublicId(@Argument @Min(1L) publicId: Long): ItemResponse{
        return itemSearchService.findItemByPublicId(publicId)
    }

    @MutationMapping
    suspend fun deleteItemByPublicId(@Argument @Min(1L) publicId: Long): String{
        val deletedTitle =  itemDeleteService.deleteItemByPublicId(publicId)
        return "$deletedTitle has been deleted"
    }

}