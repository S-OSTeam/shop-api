package sosteam.deamhome.domain.question.repository.impl

import com.querydsl.core.types.dsl.BooleanExpression
import kotlinx.coroutines.flow.Flow
import org.springframework.graphql.data.GraphQlRepository
import org.springframework.r2dbc.core.flow
import sosteam.deamhome.domain.item.handler.request.QuestionSearchRequest
import sosteam.deamhome.domain.question.entity.QQuestion
import sosteam.deamhome.domain.question.entity.Question
import sosteam.deamhome.domain.question.repository.custom.QuestionRepositoryCustom
import sosteam.deamhome.domain.question.repository.querydsl.QuestionQueryDslRepository


@GraphQlRepository
class QuestionRepositoryImpl(
	private val repository: QuestionQueryDslRepository
) : QuestionRepositoryCustom {
	private val question = QQuestion.question
	
	override fun searchQuestion(request: QuestionSearchRequest): Flow<Question> {
//		val orderSpecifiers = createOrderSpecifier(request.sort, request.direction)
		return repository.query { query ->
			query
				.select(repository.entityProjection())
				.from(question)
				.where(containsTitle(request.title))
//				.orderBy(orderSpecifiers)
				.limit(request.pageSize)
				.offset((request.pageNumber - 1L) * request.pageSize)
		}.flow()
	}
	
	private fun containsTitle(title: String?): BooleanExpression? {
		return title?.let { question.title.contains(it) }
	}
	
	private fun containsPostId(postId: String?): BooleanExpression? {
		return null
	}

//	private fun createOrderSpecifier(
//		questionSortCriteria: QuestionSortCriteria?,
//		orderDirection: Direction?
//	): OrderSpecifier<*> {
//		val direction = when (orderDirection) {
//			Direction.ASC -> Order.ASC
//			Direction.DESC -> Order.DESC
//			else -> Order.DESC // 기본값 설정
//		}
//		
//		return when (questionSortCriteria) {
//			// 정렬 기준 없으면 판매량 순으로 함
//			null -> OrderSpecifier(direction, question.sellCnt)
//			QuestionSortCriteria.SELL -> OrderSpecifier(direction, question.sellCnt)
//			QuestionSortCriteria.WISHLIST -> OrderSpecifier(direction, question.wishCnt)
//			QuestionSortCriteria.RATING -> OrderSpecifier(direction, question.avgReview)
//			QuestionSortCriteria.REVIEW -> OrderSpecifier(direction, question.reviewCnt)
//			QuestionSortCriteria.CLICK -> OrderSpecifier(direction, question.clickCnt)
//		}
//	}
	
}