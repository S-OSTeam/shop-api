package sosteam.deamhome.domain.category.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.handler.response.ItemCategoryResponse
import sosteam.deamhome.global.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.handler.response.ItemCategoryTreeResponse
import sosteam.deamhome.domain.category.handler.response.ItemCategoryTreeResponse.Companion.fromItemCategory
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.global.category.handler.response.CategoryTreeResponse
import sosteam.deamhome.global.category.provider.CategoryProvider

@Service
@Transactional
class ItemCategorySearchService (
    private val itemCategoryRepository: ItemCategoryRepository,
    private val categoryProvider: CategoryProvider<ItemCategory>
) {

    suspend fun findItemCategoryByPublicId(publicId: String): ItemCategoryResponse {
        val itemCategory = itemCategoryRepository.findByPublicId(publicId)
            ?: throw CategoryNotFoundException()
        return ItemCategoryResponse.fromItemCategory(itemCategory)
    }

    //   title 이 포함된 아이템카테고리 검색
    fun findItemCategoriesContainTitle(title: String): Flow<ItemCategoryResponse> {
        return itemCategoryRepository.findItemCategoriesContainTitle(title)
            .map { ItemCategoryResponse.fromItemCategory(it) }
    }

    // 모든 아이템 카테고리를 tree 형식으로 반환
    suspend fun findAllItemCategoriesTree(): List<ItemCategoryTreeResponse> {

        val fromCategory: (ItemCategory) -> CategoryTreeResponse<ItemCategory> = ::fromItemCategory
        return categoryProvider.findAllCategoriesTree(fromCategory)
            .map { it as ItemCategoryTreeResponse }

    }

}