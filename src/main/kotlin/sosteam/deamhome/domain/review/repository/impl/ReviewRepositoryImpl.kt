package sosteam.deamhome.domain.review.repository.impl

import com.querydsl.core.BooleanBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.review.entity.QReview
import sosteam.deamhome.domain.review.entity.Review
import sosteam.deamhome.domain.review.exception.ReviewIllegalArgumentIdException
import sosteam.deamhome.domain.review.repository.custom.ReviewRepositoryCustom
import sosteam.deamhome.domain.review.repository.querydsl.ReviewQueryDslRepository

@GraphQlRepository
class ReviewRepositoryImpl(
	private val reviewQueryDslRepository: ReviewQueryDslRepository,
) : ReviewRepositoryCustom {
	private val review = QReview.review
	
	override fun findReviews(
		reviewIds: List<Long?>?,
		userIds: List<String?>?,
		itemIds: List<String?>?
	): Flow<Review> {
		val predicate = booleanBuilder(reviewIds, userIds, itemIds)
		val finalPredicate = predicate.value ?: throw ReviewIllegalArgumentIdException()
		return reviewQueryDslRepository.findAll(finalPredicate).asFlow()
	}
	
	private fun booleanBuilder(
		reviewIds: List<Long?>?,
		userIds: List<String?>?,
		itemIds: List<String?>?
	): BooleanBuilder {
		val predicate = BooleanBuilder()
		
		reviewIds?.let {
			predicate.or(review.id.`in`(it))
		}
		userIds?.let {
			predicate.or(review.userId.`in`(it))
		}
		itemIds?.let {
			predicate.or(review.itemId.`in`(it))
		}
		return predicate
	}
	
	private fun eqUserId(userId: String?): BooleanExpression? {
		if (userId.isNullOrEmpty()) {
			return null
		}
		itemIds?.let {
			predicate.or(review.itemId.`in`(it))
		}
		return predicate
	}
}