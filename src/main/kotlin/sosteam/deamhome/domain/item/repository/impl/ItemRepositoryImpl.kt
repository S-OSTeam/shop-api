package sosteam.deamhome.domain.item.repository.impl

import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import kotlinx.coroutines.flow.Flow
import org.springframework.graphql.data.GraphQlRepository
import org.springframework.r2dbc.core.flow
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.entity.ItemSortCriteria
import sosteam.deamhome.domain.item.entity.QItem
import sosteam.deamhome.domain.item.handler.request.ItemSearchRequest
import sosteam.deamhome.domain.item.repository.custom.ItemRepositoryCustom
import sosteam.deamhome.domain.item.repository.querydsl.ItemQueryDslRepository
import sosteam.deamhome.global.attribute.Direction


@GraphQlRepository
class ItemRepositoryImpl(
	private val repository: ItemQueryDslRepository
) : ItemRepositoryCustom {
	private val item = QItem.item
	
	override fun searchItem(request: ItemSearchRequest): Flow<Item> {
		val orderSpecifiers = createOrderSpecifier(request.sort, request.direction)
		return repository.query { query ->
			query
				.select(repository.entityProjection())
				.from(item)
				.where(containsTitle(request.title))
				.orderBy(orderSpecifiers)
				.limit(request.pageSize)
				.offset((request.pageNumber - 1L) * request.pageSize)
		}.flow()
	}
	
	private fun containsTitle(title: String?): BooleanExpression? {
		return title?.let { item.title.contains(it) }
	}
	
	private fun createOrderSpecifier(
		itemSortCriteria: ItemSortCriteria?,
		orderDirection: Direction?
	): OrderSpecifier<*> {
		val direction = when (orderDirection) {
			Direction.ASC -> Order.ASC
			Direction.DESC -> Order.DESC
			else -> Order.DESC // 기본값 설정
		}
		
		return when (itemSortCriteria) {
			// 정렬 기준 없으면 판매량 순으로 함
			null -> OrderSpecifier(direction, item.sellCnt)
			ItemSortCriteria.SELL -> OrderSpecifier(direction, item.sellCnt)
			ItemSortCriteria.WISHLIST -> OrderSpecifier(direction, item.wishCnt)
			ItemSortCriteria.RATING -> OrderSpecifier(direction, item.avgReview)
			ItemSortCriteria.REVIEW -> OrderSpecifier(direction, item.reviewCnt)
			ItemSortCriteria.CLICK -> OrderSpecifier(direction, item.clickCnt)
		}
	}
	
}