package sosteam.deamhome.domain.item.repository.impl

import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.graphql.data.GraphQlRepository
import org.springframework.r2dbc.core.flow
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.entity.QItem
import sosteam.deamhome.domain.item.handler.request.ItemSearchRequest
import sosteam.deamhome.domain.item.repository.custom.ItemRepositoryCustom
import sosteam.deamhome.domain.item.repository.querydsl.ItemQueryDslRepository
import sosteam.deamhome.global.attribute.ItemSortCriteria


@GraphQlRepository
class ItemRepositoryImpl (
    private val repository: ItemQueryDslRepository
): ItemRepositoryCustom
{
    private val item = QItem.item

    override fun findItemsContainTitle(title: String): Flow<Item> {
         return repository.findAll(item.title.contains(title)).asFlow()
    }

    override fun searchItem(itemSearchRequest: ItemSearchRequest): Flow<Item> {
        val orderSpecifiers = createOrderSpecifier(itemSearchRequest.sort)
        return repository.query { query -> query
                .select(repository.entityProjection())
                .from(item)
                .where(containsTitle(itemSearchRequest.title), eqCategoryPublicId(itemSearchRequest.categoryPublicId))
                .orderBy(orderSpecifiers)
                .limit(itemSearchRequest.pageSize)
                .offset((itemSearchRequest.pageNumber - 1L) * itemSearchRequest.pageSize)
        }.flow()
    }

    private fun containsTitle(title: String?): BooleanExpression? {
        return title?.let { item.title.contains(it) }
    }

    private fun eqCategoryPublicId(categoryPublicId: String?): BooleanExpression? {
        return categoryPublicId?.let { item.categoryPublicId.eq(categoryPublicId) }
    }

    private fun createOrderSpecifier(itemSortCriteria: ItemSortCriteria?): OrderSpecifier<*> {
        return when (itemSortCriteria) {
            // 정렬 기준 없으면 판매량 순으로 함
            null -> OrderSpecifier(Order.DESC, item.sellCnt)
            ItemSortCriteria.SELL -> OrderSpecifier(Order.DESC, item.sellCnt)
            ItemSortCriteria.WISHLIST -> OrderSpecifier(Order.DESC, item.wishCnt)
            ItemSortCriteria.RATING -> OrderSpecifier(Order.DESC, item.avgReview)
            ItemSortCriteria.REVIEW -> OrderSpecifier(Order.DESC, item.reviewCnt)
            ItemSortCriteria.CLICK -> OrderSpecifier(Order.DESC, item.clickCnt)
        }
    }

}