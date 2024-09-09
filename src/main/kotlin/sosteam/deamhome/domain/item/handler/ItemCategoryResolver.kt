package sosteam.deamhome.domain.item.handler

import kotlinx.coroutines.flow.toList
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.item.handler.request.QuestionCategoryRequest
import sosteam.deamhome.domain.item.handler.request.QuestionCategoryUpdateRequest
import sosteam.deamhome.domain.item.handler.response.ItemCategoryTreeResponse
import sosteam.deamhome.domain.item.handler.response.QuestionCategoryResponse
import sosteam.deamhome.domain.item.service.ItemCategoryCreateService
import sosteam.deamhome.domain.item.service.ItemCategoryDeleteService
import sosteam.deamhome.domain.item.service.ItemCategorySearchService
import sosteam.deamhome.domain.item.service.ItemCategoryUpdateService

@RestController
class ItemCategoryResolver(
	private val itemCategoryCreateService: ItemCategoryCreateService,
	private val itemCategorySearchService: ItemCategorySearchService,
	private val itemCategoryDeleteService: ItemCategoryDeleteService,
	private val itemCategoryUpdateService: ItemCategoryUpdateService
) {
	@MutationMapping
	suspend fun createItemCategory(@Argument request: QuestionCategoryRequest): QuestionCategoryResponse {
		return itemCategoryCreateService.createCategory(request)
	}
	
	@QueryMapping
	suspend fun findItemCategoryByPublicId(@Argument publicId: String): QuestionCategoryResponse {
		return itemCategorySearchService.findItemCategoryByPublicId(publicId)
	}
	
	@QueryMapping
	suspend fun findAllItemCategoriesTree(): List<ItemCategoryTreeResponse> {
		return itemCategorySearchService.findAllItemCategoriesTree()
	}
	
	@QueryMapping
	suspend fun findSubItemCategoriesTree(@Argument categoryId: String): ItemCategoryTreeResponse {
		return itemCategorySearchService.findSubCategoriesTree(categoryId)
	}
	
	@QueryMapping
	suspend fun findItemCategoriesContainTitle(@Argument title: String): List<QuestionCategoryResponse> {
		return itemCategorySearchService.findItemCategoriesContainTitle(title).toList()
	}
	
	@MutationMapping
	suspend fun deleteItemCategoryByPublicId(@Argument publicId: String): String {
		itemCategoryDeleteService.deleteItemCategoryByPublicId(publicId)
		return publicId
	}
	
	@MutationMapping
	suspend fun updateItemCategory(@Argument request: QuestionCategoryUpdateRequest): QuestionCategoryResponse {
		return itemCategoryUpdateService.updateItemCategory(request)
	}
	
}