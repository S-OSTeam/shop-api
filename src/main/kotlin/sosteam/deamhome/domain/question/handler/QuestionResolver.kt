package sosteam.deamhome.domain.question.handler

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.item.handler.request.*
import sosteam.deamhome.domain.question.handler.response.QuestionCategoryResponse
import sosteam.deamhome.domain.question.handler.response.QuestionResponse
import sosteam.deamhome.domain.question.service.QuestionCategoryService
import sosteam.deamhome.domain.question.service.QuestionService


@RestController
class QuestionResolver(
	private val questionService: QuestionService,
	private val questionCategoryService: QuestionCategoryService
) {
	@QueryMapping
	suspend fun findQuestions(@Argument request: QuestionFilterRequest): List<QuestionResponse> {
		return questionService.findQuestions(request)
	}
	
	@QueryMapping
	suspend fun findQuestionCategories(@Argument request: QuestionFilterRequest): List<QuestionCategoryResponse> {
		return questionCategoryService.findAllQuestionCategoriesByFilter(request)
	}
	
	@QueryMapping
	suspend fun findAllQuestionCategories(): List<QuestionCategoryResponse> {
		return questionCategoryService.findAllQuestionCategoriesTree()
	}
	
	@MutationMapping
	suspend fun createQuestion(@Argument request: QuestionRequest): QuestionResponse {
		return questionService.createQuestion(request)
	}
	
	@MutationMapping
	suspend fun updateQuestion(@Argument request: QuestionUpdateRequest): QuestionResponse {
		return questionService.updateQuestion(request)
	}
	
	@MutationMapping
	suspend fun deleteQuestionByPublicId(@Argument publicId: String): Long {
		return questionService.deleteQuestionByPublicId(publicId)
	}
	
	//카테고리
	@MutationMapping
	suspend fun createQuestionCategory(@Argument request: QuestionCategoryRequest): QuestionCategoryResponse {
		return questionCategoryService.createCategory(request)
	}
	
	@MutationMapping
	suspend fun deleteQuestionCategoryByPublicId(@Argument publicId: String): Long {
		return questionCategoryService.deleteQuestionCategoryByPublicId(publicId)
	}
	
	@MutationMapping
	suspend fun updateQuestionCategory(@Argument request: QuestionCategoryUpdateRequest): QuestionCategoryResponse {
		return questionCategoryService.updateQuestionCategory(request)
	}
	
}