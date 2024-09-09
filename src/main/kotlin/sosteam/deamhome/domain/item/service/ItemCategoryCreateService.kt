package sosteam.deamhome.domain.item.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.item.entity.ItemCategory
import sosteam.deamhome.domain.item.handler.request.QuestionCategoryRequest
import sosteam.deamhome.domain.item.handler.response.QuestionCategoryResponse
import sosteam.deamhome.global.category.provider.CategoryProvider

@Service
@Transactional
class ItemCategoryCreateService(
	private val categoryProvider: CategoryProvider<ItemCategory>
) {
	
	suspend fun createCategory(request: QuestionCategoryRequest): QuestionCategoryResponse {
		
		val itemCategory = request.asDomain()
		
		// 같은 부모에 중복된 이름의 카테고리가 있거나 최대 깊이를 초과하는지 확인
		categoryProvider.validateCategory(itemCategory)
		
		val savedCategory = categoryProvider.saveCategory(itemCategory)
		
		return ItemCategoryResponse.fromItemCategory(savedCategory)
	}
	
}
