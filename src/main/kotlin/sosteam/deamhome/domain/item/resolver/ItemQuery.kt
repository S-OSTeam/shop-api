package sosteam.deamhome.domain.item.resolver

import kotlinx.coroutines.reactor.asFlux
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import sosteam.deamhome.domain.item.entity.dto.ItemDTO
import sosteam.deamhome.domain.item.service.ItemSearchService

@RestController
class ItemQuery(
    private val itemSearchService: ItemSearchService
    ) {

    @QueryMapping
    fun getItemsContainsTitle(@Argument title:String): Flux<ItemDTO>{
        return itemSearchService.getItemsContainsTitle(title).asFlux()
    }

}