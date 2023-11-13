package sosteam.deamhome.domain.item.resolver

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.item.entity.dto.request.ItemRequestDTO
import sosteam.deamhome.domain.item.entity.dto.response.ItemResponseDTO
import sosteam.deamhome.domain.item.service.ItemCreateService

@RestController
class ItemMutation(
    private val itemService: ItemCreateService
    ) {
    @MutationMapping
    suspend fun createItem(@Argument request: ItemRequestDTO) : ItemResponseDTO {
        return itemService.createItem(request)
    }

}