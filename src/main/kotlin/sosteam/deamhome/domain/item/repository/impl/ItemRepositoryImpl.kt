package sosteam.deamhome.domain.item.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.asFlow
import lombok.RequiredArgsConstructor
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.item.entity.QItem
import sosteam.deamhome.domain.item.entity.dto.ItemDTO
import sosteam.deamhome.domain.item.repository.custom.ItemRepositoryCustom
import sosteam.deamhome.domain.item.repository.querydsl.ItemQueryDslRepository

@GraphQlRepository
@RequiredArgsConstructor
class ItemRepositoryImpl (
    private val repository: ItemQueryDslRepository
): ItemRepositoryCustom
{
    private val item = QItem.item

    override fun getItemsContainsTitle(title: String): Flow<ItemDTO> {
         return repository.findAll(item.title.contains(title)).asFlow().map { it.toItemDTO() }
    }
}