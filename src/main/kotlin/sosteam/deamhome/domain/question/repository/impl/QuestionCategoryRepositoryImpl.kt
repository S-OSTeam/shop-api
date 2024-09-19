package sosteam.deamhome.domain.question.repository.impl

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.dsl.BooleanExpression
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.graphql.data.GraphQlRepository
import org.springframework.r2dbc.core.flow
import sosteam.deamhome.domain.item.handler.request.QuestionFilterRequest
import sosteam.deamhome.domain.question.entity.QQuestion.question
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
	
	override fun findAllQuestionCategoriesByFilter(filter: QuestionFilterRequest): Flow<QuestionCategory> {
		return repository.query { query ->
			query
				.select(repository.entityProjection())
				.from(question)
				.where(filterQuestionCategory(filter))
		}.flow()
	}
	
	override suspend fun findEqualsTitle(title: String): QuestionCategory? {
		return repository.findOne(questionCategory.title.eq(title)).awaitSingleOrNull()
	}
	
	private fun filterQuestionCategory(filter: QuestionFilterRequest): BooleanBuilder {
		val expr = BooleanBuilder()
		
		expr.and(question.questionType.eq(filter.questionType))
		expr.and(question.storeId.eq(filter.storeId))
		if (filter.questionCategoryFilter != null) {
			val categoryFilter = filter.questionCategoryFilter
			expr.and(containsTitle(categoryFilter.title))
			expr.and(eqPublicId(categoryFilter.publicId))
		}
		
		return expr
	}
	
	private fun containsTitle(title: String?): BooleanExpression? {
		return title?.let { questionCategory.title.contains(it) }
	}
	
	private fun eqPublicId(publicId: String?): BooleanExpression? {
		return publicId?.let { questionCategory.publicId.eq(it) }
	}
}