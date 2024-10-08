package sosteam.deamhome.domain.item.handler

import kotlinx.coroutines.flow.toList
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.item.handler.request.ItemRequest
import sosteam.deamhome.domain.item.handler.request.ItemSearchRequest
import sosteam.deamhome.domain.item.handler.request.ItemUpdateRequest
import sosteam.deamhome.domain.item.handler.response.ItemResponse
import sosteam.deamhome.domain.item.handler.response.ItemSearchResponse
import sosteam.deamhome.domain.item.service.ItemCreateService
import sosteam.deamhome.domain.item.service.ItemDeleteService
import sosteam.deamhome.domain.item.service.ItemSearchService
import sosteam.deamhome.domain.item.service.ItemUpdateService


@RestController
class ItemResolver(
	private val itemCreateService: ItemCreateService,
	private val itemSearchService: ItemSearchService,
	private val itemUpdateService: ItemUpdateService,
	private val itemDeleteService: ItemDeleteService
) {
	@MutationMapping
	suspend fun createItem(@Argument request: ItemRequest): ItemResponse {
		return itemCreateService.createItem(request)
	}
	
	@QueryMapping
	suspend fun searchItem(@Argument request: ItemSearchRequest): ItemSearchResponse {
		return request.publicId?.let {
			// publicId 가 있으면 publicId 로 검색
			val item = itemSearchService.findItemByPublicId(it)
			ItemSearchResponse(items = listOf(item), totalCount = 1)
		} ?: request.categoryPublicId?.let {
			// 카테고리를 클릭하였을 경우 검색
			val items = itemSearchService.findItemsByCategoryPublicId(it)
			
			// 총 개수 계산
			val totalCount = items.size.toLong()
			
			// 페이지네이션을 위한 계산
			val pageNumber = request.pageNumber.toInt()
			val pageSize = request.pageSize.toInt()
			val pageStart = (pageNumber - 1) * pageSize
			val pageEnd = pageStart + pageSize
			
			// 요청된 페이지에 맞게 아이템 리스트를 잘라 반환
			val paginatedItems = items.slice(pageStart until pageEnd.coerceAtMost(items.size))
			
			ItemSearchResponse(items = paginatedItems, totalCount = totalCount)
		} ?: run {
			// 검색창에 검색
			val itemsFlow = itemSearchService.searchItem(request)
			val items = itemsFlow.toList()
			
			// 총 개수 계산
			val totalCount = items.size.toLong()
			
			ItemSearchResponse(items = items, totalCount = totalCount)
		}
	}
	
	@MutationMapping
	suspend fun updateItem(@Argument request: ItemUpdateRequest): ItemResponse {
		return itemUpdateService.updateItem(request)
	}
	
	@MutationMapping
	suspend fun deleteItemByPublicId(@Argument publicId: String): String {
		val deletedTitle = itemDeleteService.deleteItemByPublicId(publicId)
		return "deleted"
	}
	
}