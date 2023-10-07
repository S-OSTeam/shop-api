package sosteam.deamhome.domain.item.resolver

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.item.entity.dto.ItemDTO
import sosteam.deamhome.domain.item.resolver.request.ItemCreateRequest
import sosteam.deamhome.domain.item.service.ItemService

@RestController
class ItemMutation(
    private val itemService: ItemService
    ) {
    @MutationMapping
    suspend fun createItem(@Argument request: ItemCreateRequest) : ItemDTO{
        if (request == null){
            println("request 가 null 임")
            return ItemDTO()
        }
        return itemService.createItem(request)
    }

}