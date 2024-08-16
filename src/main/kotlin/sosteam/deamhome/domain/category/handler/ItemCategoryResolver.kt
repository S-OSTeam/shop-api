package sosteam.deamhome.domain.category.handler

import kotlinx.coroutines.flow.toList
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.category.handler.request.ItemCategoryRequest
import sosteam.deamhome.domain.category.handler.request.ItemCategoryUpdateRequest
import sosteam.deamhome.domain.category.handler.response.ItemCategoryResponse
import sosteam.deamhome.domain.category.handler.response.ItemCategoryTreeResponse
import sosteam.deamhome.domain.category.service.ItemCategoryCreateService
import sosteam.deamhome.domain.category.service.ItemCategoryDeleteService
import sosteam.deamhome.domain.category.service.ItemCategorySearchService
import sosteam.deamhome.domain.category.service.ItemCategoryUpdateService

@RestController
class ItemCategoryResolver(
	private val itemCategoryCreateService: ItemCategoryCreateService,
	private val itemCategorySearchService: ItemCategorySearchService,
	private val itemCategoryDeleteService: ItemCategoryDeleteService,
	private val itemCategoryUpdateService: ItemCategoryUpdateService
) {
	@MutationMapping
	suspend fun createItemCategory(@Argument request: ItemCategoryRequest): ItemCategoryResponse {
		return itemCategoryCreateService.createCategory(request)
	}
	
	@QueryMapping
	suspend fun findItemCategoryByPublicId(@Argument publicId: String): ItemCategoryResponse {
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
	suspend fun findItemCategoriesContainTitle(@Argument title: String): List<ItemCategoryResponse> {
		return itemCategorySearchService.findItemCategoriesContainTitle(title).toList()
	}
	
	@MutationMapping
	suspend fun deleteItemCategoryByPublicId(@Argument publicId: String): String {
		itemCategoryDeleteService.deleteItemCategoryByPublicId(publicId)
		return publicId
	}
	
	@MutationMapping
	suspend fun updateItemCategory(@Argument request: ItemCategoryUpdateRequest): ItemCategoryResponse {
		return itemCategoryUpdateService.updateItemCategory(request)
	}
	
}