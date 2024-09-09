package sosteam.deamhome.domain.item.service

import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.item.entity.ItemCategory
import sosteam.deamhome.domain.item.handler.request.QuestionCategoryUpdateRequest
import sosteam.deamhome.domain.item.handler.response.QuestionCategoryResponse
import sosteam.deamhome.domain.item.repository.ItemCategoryRepository
import sosteam.deamhome.global.category.exception.CategoryNotFoundException
import sosteam.deamhome.global.category.exception.MaxDepthExceedException
import sosteam.deamhome.global.category.provider.CategoryProvider


@Service
@Transactional
class ItemCategoryUpdateService(
	private val itemCategoryRepository: ItemCategoryRepository,
	private val categoryProvider: CategoryProvider<ItemCategory>
) {
	suspend fun updateItemCategory(request: QuestionCategoryUpdateRequest): QuestionCategoryResponse {
		val itemCategory = itemCategoryRepository.findByPublicId(request.publicId)
			?: throw CategoryNotFoundException()
		
		request.title?.let { itemCategory.title = it }
		
		request.parentPublicId?.let { parentPublicId ->
			if (itemCategory.parentPublicId == parentPublicId) {
				// 이미 같은 부모에 있음
				return@let
			}
			if (itemCategory.publicId == parentPublicId) {
				// 최상위로 이동
				categoryProvider.validateTopCategory(itemCategory.title)
			} else {
				// 다른 카테고리 아래로 이동
				// 최대 깊이가 3이라서 사이클은 발생하지 않음
				val parentDepth =
					categoryProvider.validateParentCategory(parentPublicId, itemCategory.title, itemCategory.maxDepth)
				val depth = getChildrenDepth(itemCategory)
				if (parentDepth + depth > itemCategory.maxDepth + 1)
					throw MaxDepthExceedException()
			}
			itemCategory.parentPublicId = parentPublicId
		}
		val updated = itemCategoryRepository.save(itemCategory)
		return ItemCategoryResponse.fromItemCategory(updated)
	}
	
	private suspend fun getChildrenDepth(category: ItemCategory): Int {
		return getChildrenDepthRecursive(category, 1)
	}
	
	private suspend fun getChildrenDepthRecursive(category: ItemCategory, depth: Int): Int {
		val children = itemCategoryRepository.findByParentPublicId(category.publicId).toList()
		if (children.isEmpty()) {
			return depth
		}
		var maxDepth = depth
		for (child in children) {
			if (child.isTop())
				continue
			val childDepth = getChildrenDepthRecursive(child, depth + 1)
			if (childDepth > maxDepth) {
				maxDepth = childDepth
			}
		}
		return maxDepth
	}
	
}