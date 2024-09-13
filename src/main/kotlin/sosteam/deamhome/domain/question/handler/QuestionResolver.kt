package sosteam.deamhome.domain.question.handler

import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.question.service.QuestionCategoryService
import sosteam.deamhome.domain.question.service.QuestionService


@RestController
class QuestionResolver(
	private val questionService: QuestionService,
	private val questionCategoryService: QuestionCategoryService
) {
//	@QueryMapping
//	suspend fun findQuestions(@Argument request: QuestionSearchRequest): List<QuestionCategoryResponse> {
//		return questionService.
//	}

//	@QueryMapping
//	suspend fun findQuestionCategories(@Argument request: QuestionSearchRequest): List<QuestionCategoryResponse> {
//
//	}
//
//
//	@MutationMapping
//	suspend fun createQuestion(@Argument request: QuestionRequest) : QuestionResponse {
//
//	}
//
//	@MutationMapping
//	suspend fun updateQuestion(@Argument request: QuestionUpdateRequest): String {
//
//	}
//
//	@MutationMapping
//	suspend fun deleteQuestionByPublicId(publicId: String): String {
//
//	}
//
//	//카테고리
//	@MutationMapping
//	suspend fun createQuestionCategory(request: QuestionCategoryRequest): QuestionCategoryResponse {
//
//	}
//
//	@MutationMapping
//	suspend fun deleteQuestionCategoryByPublicId(publicId: String!) : String {
//		return ""
//	}
//
//	@MutationMapping
//	suspend fun updateQuestionCategory(request: QuestionCategoryUpdateRequest): QuestionCategoryResponse {
//		return
//	}
	
}