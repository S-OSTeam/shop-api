package sosteam.deamhome.domain.category.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.handler.request.ItemCategoryRequest
import sosteam.deamhome.domain.category.handler.response.ItemCategoryResponse
import sosteam.deamhome.global.category.provider.CategoryProvider

@Service
@Transactional
class ItemCategoryCreateService(
    private val categoryProvider: CategoryProvider<ItemCategory>
) {

    suspend fun createCategory(request: ItemCategoryRequest): ItemCategoryResponse {

        val itemCategory = request.asDomain()

        // 같은 부모에 중복된 이름의 카테고리가 있거나 최대 깊이를 초과하는지 확인
        categoryProvider.validateCategory(itemCategory)

        val savedCategory = categoryProvider.saveCategory(itemCategory)

        return ItemCategoryResponse.fromItemCategory(savedCategory)
    }

}
