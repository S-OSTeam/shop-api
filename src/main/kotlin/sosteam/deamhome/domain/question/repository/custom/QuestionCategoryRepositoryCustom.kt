package sosteam.deamhome.domain.question.repository.custom

import kotlinx.coroutines.flow.Flow
import sosteam.deamhome.domain.question.entity.QuestionCategory

interface QuestionCategoryRepositoryCustom {
	
	fun findQuestionCategoriesContainTitle(title: String): Flow<QuestionCategory>
	
	fun findAllQuestionCategoriesByTitle(title: String): Flow<QuestionCategory>
	
	suspend fun findEqualsTitle(title: String): QuestionCategory?
	
}