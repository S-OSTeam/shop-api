package sosteam.deamhome.domain.item.resolver

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.item.entity.dto.request.ItemRequestDTO
import sosteam.deamhome.domain.item.entity.dto.response.ItemResponseDTO
import sosteam.deamhome.domain.item.service.ItemCreateService
import sosteam.deamhome.domain.item.service.ItemDeleteService

@RestController
class ItemMutation(
    private val itemCreateService: ItemCreateService,
    private val itemDeleteService: ItemDeleteService
    ) {
    @MutationMapping
    suspend fun createItem(@Argument request: ItemRequestDTO) : ItemResponseDTO {
        return itemCreateService.createItem(request)
    }

    @MutationMapping
    suspend fun deleteItem(@Argument id: String){
        itemDeleteService.deleteItem(id)
    }

}