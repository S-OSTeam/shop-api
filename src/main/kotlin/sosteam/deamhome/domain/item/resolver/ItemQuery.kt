package sosteam.deamhome.domain.item.resolver

import kotlinx.coroutines.reactor.asFlux
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import sosteam.deamhome.domain.item.entity.dto.ItemDTO
import sosteam.deamhome.domain.item.entity.dto.response.ItemResponseDTO
import sosteam.deamhome.domain.item.service.ItemSearchService

@RestController
class ItemQuery(
    private val itemSearchService: ItemSearchService
    ) {

    @QueryMapping
    fun getItemsContainsTitle(@Argument title: String): Flux<ItemResponseDTO>{
        return itemSearchService.getItemsContainsTitle(title).asFlux()
    }

    @QueryMapping
    suspend fun getItemsByOption(@Argument categoryTitle: String?, @Argument detailTitle: String?, @Argument itemTitle: String?): Flux<ItemResponseDTO> {
        return itemSearchService.getItemsByOption(categoryTitle, detailTitle, itemTitle).asFlux()

    }

}