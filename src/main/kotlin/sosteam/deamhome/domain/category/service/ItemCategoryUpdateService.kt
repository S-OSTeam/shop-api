package sosteam.deamhome.domain.category.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.handler.request.ItemCategoryUpdateRequest
import sosteam.deamhome.domain.category.handler.response.ItemCategoryResponse
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.global.category.exception.CategoryNotFoundException
import sosteam.deamhome.global.category.provider.CategoryProvider


@Service
@Transactional
class ItemCategoryUpdateService (
    private val itemCategoryRepository: ItemCategoryRepository,
    private val categoryProvider: CategoryProvider<ItemCategory>
) {
    suspend fun updateItemCategory(request: ItemCategoryUpdateRequest): ItemCategoryResponse {
        println(request)
        val itemCategory = itemCategoryRepository.findByPublicId(request.publicId)
            ?: throw CategoryNotFoundException()

        request.title?.let { itemCategory.title = it }

        request.parentPublicId?.let { parentPublicId ->
            if (itemCategory.parentPublicId == parentPublicId) {
                // 이미 같은 부모에 있음
                return@let
            }
            if (itemCategory.publicId == parentPublicId) {
                categoryProvider.validateTopCategory(itemCategory.title)
            }
            else {
                categoryProvider.validateParentCategory(parentPublicId, itemCategory.title, itemCategory.maxDepth)
            }
            itemCategory.parentPublicId = parentPublicId
        }
        val updated = itemCategoryRepository.save(itemCategory)
        return ItemCategoryResponse.fromItemCategory(updated)
    }


}