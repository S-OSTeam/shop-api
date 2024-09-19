package sosteam.deamhome.domain.item.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.item.entity.ItemCategory
import sosteam.deamhome.domain.item.handler.response.ItemCategoryResponse
import sosteam.deamhome.domain.item.handler.response.ItemCategoryTreeResponse
import sosteam.deamhome.domain.item.handler.response.ItemCategoryTreeResponse.Companion.fromItemCategory
import sosteam.deamhome.domain.item.repository.ItemCategoryRepository
import sosteam.deamhome.global.category.exception.CategoryNotFoundException
import sosteam.deamhome.global.category.factory.CategoryProviderFactory
import sosteam.deamhome.global.category.handler.response.CategoryTreeResponse
import sosteam.deamhome.global.category.provider.CategoryProvider

@Service
@Transactional
class ItemCategorySearchService(
	private val itemCategoryRepository: ItemCategoryRepository,
	private val categoryProviderFactory: CategoryProviderFactory
) {
	
	val categoryProvider = CategoryProvider(categoryProviderFactory, ItemCategory::class.java)
	
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
	
	
	suspend fun findSubCategoriesTree(categoryId: String): ItemCategoryTreeResponse {
		// 카테고리 트리를 구성하는 함수를 정의
		val fromCategory: (ItemCategory) -> CategoryTreeResponse<ItemCategory> = ::fromItemCategory
		
		// CategoryProvider의 findSubCategoriesTree 메소드를 호출하여 카테고리 트리 구성
		val categoryTree = categoryProvider.findAllSubCategoriesTree(categoryId, fromCategory)
		
		// 결과 반환
		return categoryTree as ItemCategoryTreeResponse  // 안전한 타입 캐스팅
	}
	
}