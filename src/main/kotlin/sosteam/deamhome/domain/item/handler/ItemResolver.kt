package sosteam.deamhome.domain.item.handler

import kotlinx.coroutines.flow.toList
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.item.handler.request.ItemRequest
import sosteam.deamhome.domain.item.handler.request.ItemSearchRequest
import sosteam.deamhome.domain.item.handler.request.ItemUpdateRequest
import sosteam.deamhome.domain.item.handler.response.ItemResponse
import sosteam.deamhome.domain.item.service.ItemCreateService
import sosteam.deamhome.domain.item.service.ItemDeleteService
import sosteam.deamhome.domain.item.service.ItemSearchService
import sosteam.deamhome.domain.item.service.ItemUpdateService


@RestController
class ItemResolver(
    private val itemCreateService: ItemCreateService,
    private val itemSearchService: ItemSearchService,
    private val itemUpdateService: ItemUpdateService,
    private val itemDeleteService: ItemDeleteService
    ) {
    @MutationMapping
    suspend fun createItem(@Argument request: ItemRequest) : ItemResponse {
        return itemCreateService.createItem(request)
    }

    @QueryMapping
    suspend fun searchItem(@Argument request: ItemSearchRequest): List<ItemResponse> {
        return request.publicId?.let {
            // publicId 가 있으면 publicId 로 검색
            listOf(itemSearchService.findItemByPublicId(it))
        } ?: request.categoryPublicId?.let {
            // categoryPublicId 가 있으면 categoryPublicId 로 카테고리 하위 모든 아이템들을 검색
            // 검색 창에 검색하는 경우가 아니라 카테고리를 클릭하였을 경우 검색임
            itemSearchService.findItemsByCategoryPublicId(it)
        } ?:
            //검색창에 검색
            itemSearchService.searchItem(request).toList()
    }

    @MutationMapping
    suspend fun updateItem(@Argument request: ItemUpdateRequest): ItemResponse {
        return itemUpdateService.updateItem(request)
    }

    @MutationMapping
    suspend fun deleteItemByPublicId(@Argument publicId: String): String {
        val deletedTitle =  itemDeleteService.deleteItemByPublicId(publicId)
        return "deleted"
    }

}