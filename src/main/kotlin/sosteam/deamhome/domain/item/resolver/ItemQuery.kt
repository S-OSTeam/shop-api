package sosteam.deamhome.domain.item.resolver

import lombok.RequiredArgsConstructor
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.item.entity.dto.ItemDTO
import sosteam.deamhome.domain.item.service.ItemService

@RestController
@RequiredArgsConstructor
class ItemQuery(
    private val itemService: ItemService
    ) {
    @QueryMapping
    suspend fun getItemByTitle(title: String): ItemDTO{
        println("before")
//    ResponseEntity<Any> {
        return itemService.findItemByTitle(title)
    }

}