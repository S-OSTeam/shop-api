package sosteam.deamhome.domain.item.resolver

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.category.dto.ItemCategoryDTO
import sosteam.deamhome.domain.item.entity.dto.ItemDTO
import sosteam.deamhome.domain.item.resolver.request.ItemCreateRequest
import sosteam.deamhome.domain.item.service.ItemCreateService

@RestController
class ItemMutation(
    private val itemService: ItemCreateService
    ) {
    @MutationMapping
    suspend fun createItem(@Argument request: ItemCreateRequest) : ItemDTO {
        return itemService.createItem(request)
    }

}