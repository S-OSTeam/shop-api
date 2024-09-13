package sosteam.deamhome.domain.question.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.question.entity.QQuestionCategory
import sosteam.deamhome.domain.question.entity.QuestionCategory
import sosteam.deamhome.domain.question.repository.custom.QuestionCategoryRepositoryCustom
import sosteam.deamhome.domain.question.repository.querydsl.QuestionCategoryQueryDslRepository

@GraphQlRepository
class QuestionCategoryRepositoryImpl(
	private val repository: QuestionCategoryQueryDslRepository
) : QuestionCategoryRepositoryCustom {
	
	private val questionCategory = QQuestionCategory.questionCategory
	
	override fun findQuestionCategoriesContainTitle(title: String): Flow<QuestionCategory> {
		return repository.findAll(questionCategory.title.contains(title)).asFlow()
	}
	
	override fun findAllQuestionCategoriesByTitle(title: String): Flow<QuestionCategory> {
		return repository.findAll(questionCategory.title.eq(title)).asFlow()
	}
	
	override suspend fun findEqualsTitle(title: String): QuestionCategory? {
		return repository.findOne(questionCategory.title.eq(title)).awaitSingleOrNull()
	}
	
}