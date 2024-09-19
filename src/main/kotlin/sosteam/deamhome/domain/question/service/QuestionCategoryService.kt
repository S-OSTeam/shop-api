package sosteam.deamhome.domain.question.service

import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.item.handler.request.QuestionCategoryRequest
import sosteam.deamhome.domain.item.handler.request.QuestionCategoryUpdateRequest
import sosteam.deamhome.domain.item.handler.request.QuestionFilterRequest
import sosteam.deamhome.domain.question.entity.QuestionCategory
import sosteam.deamhome.domain.question.handler.response.QuestionCategoryResponse
import sosteam.deamhome.domain.question.repository.QuestionCategoryRepository
import sosteam.deamhome.domain.question.repository.QuestionRepository
import sosteam.deamhome.global.category.exception.CategoryDeleteFailException
import sosteam.deamhome.global.category.exception.CategoryNotFoundException
import sosteam.deamhome.global.category.exception.MaxDepthExceedException
import sosteam.deamhome.global.category.factory.CategoryProviderFactory
import sosteam.deamhome.global.category.handler.response.CategoryTreeResponse
import sosteam.deamhome.global.category.provider.CategoryProvider

@Service
class QuestionCategoryService(
	private val questionCategoryRepository: QuestionCategoryRepository,
	private val questionRepository: QuestionRepository,
	private val categoryProviderFactory: CategoryProviderFactory
) {
	val categoryProvider = CategoryProvider(categoryProviderFactory, QuestionCategory::class.java)
	
	@Transactional
	suspend fun createCategory(request: QuestionCategoryRequest): QuestionCategoryResponse {
		val questionCategory = request.asDomain()
		
		categoryProvider.validateCategory(questionCategory)
		
		val savedCCategory = categoryProvider.saveCategory(questionCategory)
		
		return QuestionCategoryResponse.fromQuestionCategory(savedCCategory)
	}
	
	@Transactional
	suspend fun updateQuestionCategory(request: QuestionCategoryUpdateRequest): QuestionCategoryResponse {
		val questionCategory = questionCategoryRepository.findByPublicId(request.publicId)
			?: throw CategoryNotFoundException()
		
		request.title?.let { questionCategory.title = it }
		
		request.parentPublicId?.let { parentPublicId ->
			if (questionCategory.parentPublicId == parentPublicId) {
				// 이미 같은 부모에 있음
				return@let
			}
			if (questionCategory.publicId == parentPublicId) {
				// 최상위로 이동
				categoryProvider.validateTopCategory(questionCategory.title)
			} else {
				// 다른 카테고리 아래로 이동
				// 최대 깊이가 3이라서 사이클은 발생하지 않음
				val parentDepth =
					categoryProvider.validateParentCategory(
						parentPublicId,
						questionCategory.title,
						questionCategory.maxDepth
					)
				val depth = getChildrenDepth(questionCategory)
				if (parentDepth + depth > questionCategory.maxDepth + 1)
					throw MaxDepthExceedException()
			}
			questionCategory.parentPublicId = parentPublicId
		}
		val updated = questionCategoryRepository.save(questionCategory)
		return QuestionCategoryResponse.fromQuestionCategory(updated)
	}
	
	@Transactional
	suspend fun deleteQuestionCategoryByPublicId(publicId: String): Long {
		// 삭제할 카테고리가 존재하는지 확인
		questionCategoryRepository.findByPublicId(publicId)
			?: throw CategoryNotFoundException()
		
		// 카테고리 하위에 카테고리가 있으면 삭제 불가
		val childrenCategories = questionCategoryRepository.findByParentPublicId(publicId).toList()
			.filterNot { it.isTop() }
		if (childrenCategories.isNotEmpty())
			throw CategoryDeleteFailException("삭제하려는 카테고리 하위에 다른 카테고리가 있습니다.")
		
		// 카테고리 하위에 아이템이 있으면 삭제 불가
		val childrenQuestions = questionRepository.findByCategoryPublicId(publicId).toList()
		if (childrenQuestions.isNotEmpty())
			throw CategoryDeleteFailException("삭제하려는 카테고리 하위에 아이템이 있습니다.")
		
		return questionCategoryRepository.deleteByPublicId(publicId)
	}
	
	suspend fun findAllQuestionCategoriesByFilter(request: QuestionFilterRequest): List<QuestionCategoryResponse> {
		return questionCategoryRepository.findAllQuestionCategoriesByFilter(request).toList()
			.map { QuestionCategoryResponse.fromQuestionCategory(it) }
	}
	
	suspend fun findAllQuestionCategoriesTree(): List<QuestionCategoryResponse> {
		
		val fromCategory: (QuestionCategory) -> CategoryTreeResponse<QuestionCategory> =
			QuestionCategoryResponse.Companion::fromQuestionCategory
		return categoryProvider.findAllCategoriesTree(fromCategory)
			.map { it as QuestionCategoryResponse }
	}
	
	private suspend fun getChildrenDepth(category: QuestionCategory): Int {
		return getChildrenDepthRecursive(category, 1)
	}
	
	private suspend fun getChildrenDepthRecursive(category: QuestionCategory, depth: Int): Int {
		val children = questionCategoryRepository.findByParentPublicId(category.publicId).toList()
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