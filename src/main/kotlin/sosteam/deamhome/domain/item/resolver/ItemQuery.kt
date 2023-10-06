package sosteam.deamhome.domain.item.resolver

import lombok.RequiredArgsConstructor
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.item.service.ItemService

@RestController
@RequiredArgsConstructor
class ItemQuery(
    private val itemService: ItemService
    ) {
    @QueryMapping
    suspend fun getItemByTitle(title: String): ResponseEntity<Any> {
        val result = itemService.findItemByTitle(title)
        return if(result.title != null){
            ResponseEntity(result, HttpStatus.OK)
        } else {
            ResponseEntity("not found", HttpStatus.NOT_FOUND)
        }
    }

}