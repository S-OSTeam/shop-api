package sosteam.deamhome.domain.item.resolver

import lombok.RequiredArgsConstructor
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.item.entity.dto.ItemDTO
import sosteam.deamhome.domain.item.service.ItemCreateService

@RestController
class ItemQuery(
    private val itemService: ItemCreateService
    ) {
//    @QueryMapping
//    suspend fun getItemByTitle(@Argument title: String): ItemDTO{
//        println("before")
////    ResponseEntity<Any> {
//        return itemService.findItemByTitle(title)
//    }

}