package sosteam.deamhome.domain.category.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.handler.response.ItemCategoryResponse
import sosteam.deamhome.global.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.handler.response.ItemCategoryTreeResponse
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository

@Service
@Transactional
class ItemCategorySearchService (
    private val itemCategoryRepository: ItemCategoryRepository
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
        val itemCategories = itemCategoryRepository.findAllItemCategories().toList()

        val map = mutableMapOf<String, ItemCategoryTreeResponse>()

        // key 는 publicId, value 는 ItemCategoryTreeResponse 로 하는 map
        itemCategories.forEach { itemCategory ->
            map[itemCategory.publicId] = ItemCategoryTreeResponse.fromItemCategory(itemCategory)
        }

        // 부모 카테고리의 children 에 자식 카테고리 추가
        itemCategories
            .filterNot(ItemCategory::isTop)
            .forEach { itemCategory ->
                val parent = map[itemCategory.parentPublicId]
                val child = map[itemCategory.publicId]
                parent!!.children.add(child!!)
            }

        // 최상위 카테고리만 모아서 list 로 반환
        val roots = itemCategories
            .filter { it.isTop() }
            .map { map[it.publicId]!! }.toList()

        return roots
    }

}