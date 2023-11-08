package sosteam.deamhome.domain.item.repository.impl

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.asFlow
import lombok.RequiredArgsConstructor
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.item.entity.QItem
import sosteam.deamhome.domain.item.entity.dto.ItemDTO
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

    override fun getItemsContainsTitle(title: String): Flow<ItemDTO> {
         return repository.findAll(item.title.contains(title)).asFlow().map { it.toItemDTO() }
    }

    override fun getItemsByOption(itemIdList: List<String>?, title: String?): Flow<ItemDTO> {

        //둘 다 null 이면 모두 검색하는게 맞나? 아니면 검색 조건을 넣어주세요 라고 해야하나?
        //둘 다 null 이면 graphql 에서 syntax error 남
        return repository.findAll(searchOption(itemIdList, title)).asFlow().map { it.toItemDTO() }
    }

    private fun searchOption(itemIdList: List<String>?, title: String?): Predicate{
        val builder = BooleanBuilder()
        if(itemIdList != null){
            builder.and(item.id.`in`(itemIdList))
            log().info("builder add id")
        }
        if(!title.isNullOrBlank() ){
            builder.and(item.title.eq(title))
            log().info("builder add title")
        }
        return builder
    }

}