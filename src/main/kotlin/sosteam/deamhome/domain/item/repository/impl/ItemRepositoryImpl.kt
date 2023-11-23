package sosteam.deamhome.domain.item.repository.impl

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import lombok.RequiredArgsConstructor
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.entity.QItem
import sosteam.deamhome.domain.item.repository.custom.ItemRepositoryCustom
import sosteam.deamhome.domain.item.repository.querydsl.ItemQueryDslRepository
import sosteam.deamhome.global.provider.log

@GraphQlRepository
@RequiredArgsConstructor
class ItemRepositoryImpl (
    private val repository: ItemQueryDslRepository
): ItemRepositoryCustom
{
    private val item = QItem.item

    override fun getItemsContainsTitle(title: String): Flow<Item> {
         return repository.findAll(item.title.contains(title)).asFlow()
    }

    override fun getItemsByOption(itemIdList: List<String>?, title: String?): Flow<Item> {
        return repository.findAll(searchOption(itemIdList, title)).asFlow()
    }

    private fun searchOption(itemIdList: List<String>?, title: String?): Predicate{
        val builder = BooleanBuilder()
        if(itemIdList != null){
            builder.and(item.id.`in`(itemIdList))
            log().debug("builder add id")
        }
        if(!title.isNullOrBlank() ){
            builder.and(item.title.eq(title))
            log().debug("builder add title")
        }
        return builder
    }

}