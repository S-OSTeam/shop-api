package sosteam.deamhome.domain.review.repository.impl

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.BooleanExpression
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import sosteam.deamhome.domain.review.entity.QReview
import sosteam.deamhome.domain.review.entity.Review
import sosteam.deamhome.domain.review.exception.ReviewIllegalArgumentIdException
import sosteam.deamhome.domain.review.repository.custom.ReviewRepositoryCustom
import sosteam.deamhome.domain.review.repository.querydsl.ReviewQueryDslRepository

class ReviewRepositoryImpl(
	private val reviewQueryDslRepository: ReviewQueryDslRepository,
) : ReviewRepositoryCustom {
	private val review = QReview.review
	
	override fun findReviews(reviewId: String?, userId: String?, itemId: String?): Flow<Review> {
		val predicate = BooleanBuilder()
		
		eqReviewId(reviewId)?.let { predicate.and(it) }
		eqUserId(userId)?.let { predicate.and(it) }
		eqItemId(itemId)?.let { predicate.and(it) }
		
		val finalPredicate: Predicate = predicate.value ?: throw ReviewIllegalArgumentIdException()
		
		return reviewQueryDslRepository.findAll(finalPredicate).asFlow()
	}
	
	private fun eqReviewId(reviewId: String?): BooleanExpression? {
		if (reviewId.isNullOrEmpty()) {
			return null
		}
		return review.id.eq(reviewId)
	}
	
	private fun eqUserId(userId: String?): BooleanExpression? {
		if (userId.isNullOrEmpty()) {
			return null
		}
		return review.userId.eq(userId)
	}
	
	private fun eqItemId(itemId: String?): BooleanExpression? {
		if (itemId.isNullOrEmpty()) {
			return null
		}
		return review.itemId.eq(itemId)
	}
}