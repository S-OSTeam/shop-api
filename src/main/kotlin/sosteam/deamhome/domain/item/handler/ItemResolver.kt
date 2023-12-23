package sosteam.deamhome.domain.item.handler

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.item.handler.request.ItemRequest
import sosteam.deamhome.domain.item.handler.response.ItemResponse
import sosteam.deamhome.domain.item.service.ItemCreateService


@RestController
class ItemResolver(
    private val itemCreateService: ItemCreateService,
//    private val itemDeleteService: ItemDeleteService,
//    private val itemSearchService: ItemSearchService
    ) {
    @MutationMapping
    suspend fun createItem(@Argument request: ItemRequest) : ItemResponse {
        return itemCreateService.createItem(request)
    }

//    @MutationMapping
//    suspend fun deleteItem(@Argument id: String) : Boolean{
//        itemDeleteService.deleteItem(id)
//        return true
//    }
//
//    @QueryMapping
//    suspend fun getItemsContainsTitle(@Argument title: String): List<ItemResponseDTO>{
//        return itemSearchService.getItemsContainsTitle(title).toList()
//    }
//
//    @QueryMapping
//    suspend fun getItemsByOption(@Argument categoryTitle: String?, @Argument detailTitle: String?, @Argument itemTitle: String?): List<ItemResponseDTO> {
//        // 인자로 모두 null 이 들어오면 전체 검색함
//        //검색조건 추가하라고 하는게 나을듯
//        //graphql 에서 인자로 아무것도 안넣으면 syntax error 라서 null 이라도 넣으면 작동함
//        return itemSearchService.getItemsByOption(categoryTitle, detailTitle, itemTitle).toList()
//    }

}