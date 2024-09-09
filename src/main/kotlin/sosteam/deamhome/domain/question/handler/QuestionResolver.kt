package sosteam.deamhome.domain.question.handler

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.item.handler.request.*
import sosteam.deamhome.domain.item.handler.response.QuestionCategoryResponse
import sosteam.deamhome.domain.question.handler.response.QuestionResponse


@RestController
class QuestionResolver {
	@QueryMapping
	suspend fun findQuestionCategories(@Argument request: QuestionSearchRequest): List<QuestionCategoryResponse> {
	
	}
	
	@QueryMapping
	suspend fun findQuestions(@Argument request: QuestionSearchRequest): List<QuestionCategoryResponse> {
	
	}
	
	@MutationMapping
	suspend fun createQuestion(@Argument request: QuestionRequest) : QuestionResponse {
	
	}
	
	@MutationMapping
	suspend fun updateQuestion(@Argument request: QuestionUpdateRequest): String {
	
	}
	
	@MutationMapping
	suspend fun deleteQuestionByPublicId(publicId: String): String {
	
	}
	
	//카테고리
	@MutationMapping
	suspend fun createQuestionCategory(request: QuestionCategoryRequest): QuestionCategoryResponse {
	
	}
	
	@MutationMapping
	suspend fun deleteQuestionCategoryByPublicId(publicId: String!) : String {
	
	}
	
	@MutationMapping
	suspend fun updateQuestionCategory(request: QuestionCategoryUpdateRequest): QuestionCategoryResponse {
	
	}
	
}