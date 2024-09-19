package sosteam.deamhome.domain.question.repository.impl

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.dsl.BooleanExpression
import kotlinx.coroutines.flow.Flow
import org.springframework.graphql.data.GraphQlRepository
import org.springframework.r2dbc.core.flow
import sosteam.deamhome.domain.item.handler.request.QuestionFilterRequest
import sosteam.deamhome.domain.question.entity.QQuestion
import sosteam.deamhome.domain.question.entity.Question
import sosteam.deamhome.domain.question.repository.custom.QuestionRepositoryCustom
import sosteam.deamhome.domain.question.repository.querydsl.QuestionQueryDslRepository
import sosteam.deamhome.global.attribute.PostType


@GraphQlRepository
class QuestionRepositoryImpl(
	private val repository: QuestionQueryDslRepository
) : QuestionRepositoryCustom {
	private val question = QQuestion.question
	
	override fun findAllQuestionsByFilter(
		filter: QuestionFilterRequest,
		questionCategories: List<String>
	): Flow<Question> {
		return repository.query { query ->
			query
				.select(repository.entityProjection())
				.from(question)
				.where(filterQuestion(filter).and(inCategories(questionCategories)))
//				.orderBy(orderSpecifiers)
				.limit(filter.pageSize)
				.offset((filter.pageNumber - 1L) * filter.pageSize)
		}.flow()
	}
	
	private fun filterQuestion(filter: QuestionFilterRequest): BooleanBuilder {
		val expr = BooleanBuilder()
		
		expr.and(question.questionType.eq(filter.questionType))
		expr.and(question.storeId.eq(filter.storeId))
		if (filter.questionFilter != null) {
			val questionFilter = filter.questionFilter
			expr.and(containsTitle(questionFilter.title))
			expr.and(containsContent(questionFilter.content))
			expr.and(eqPublicId(questionFilter.publicId))
			expr.and(eqPostId(questionFilter.postId))
			expr.and(eqItemId(questionFilter.itemId))
			expr.and(question.questionStatus.`in`(questionFilter.questionStatus))
			expr.and(eqQuestionIsCompleted(questionFilter.questionIsCompleted))
			expr.and(containsPostType(questionFilter.postType))
		}
		
		return expr
	}
	
	private fun containsTitle(title: String?): BooleanExpression? {
		return title?.let { question.title.contains(it) }
	}
	
	private fun containsContent(content: String?): BooleanExpression? {
		return content?.let { question.content.contains(it) }
	}
	
	private fun containsPostType(postTypes: List<PostType>?): BooleanExpression? {
		if (!postTypes.isNullOrEmpty()) {
			return question.postType.`in`(postTypes)
		}
		return null
	}
	
	private fun eqQuestionIsCompleted(questionIsCompleted: Boolean?): BooleanExpression? {
		return questionIsCompleted?.let { question.questionIsCompleted.eq(it) }
	}
	
	private fun eqPostId(postId: String?): BooleanExpression? {
		return postId?.let { question.postId.eq(it) }
	}
	
	private fun eqPublicId(publicId: String?): BooleanExpression? {
		return publicId?.let { question.publicId.eq(it) }
	}
	
	private fun eqItemId(itemId: String?): BooleanExpression? {
		return itemId?.let { question.itemId.eq(it) }
	}
	
	private fun inCategories(categoryIds: List<String>): BooleanExpression? {
		if (categoryIds.isNotEmpty()) {
			return question.categoryPublicId.`in`(categoryIds)
		}
		return null
	}
	
}