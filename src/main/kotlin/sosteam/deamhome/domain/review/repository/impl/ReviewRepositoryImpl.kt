package sosteam.deamhome.domain.review.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import sosteam.deamhome.domain.review.entity.QReview
import sosteam.deamhome.domain.review.entity.Review
import sosteam.deamhome.domain.review.repository.custom.ReviewRepositoryCustom
import sosteam.deamhome.domain.review.repository.querydsl.ReviewQueryDslRepository

class ReviewRepositoryImpl(
	private val reviewQueryDslRepository: ReviewQueryDslRepository,
) : ReviewRepositoryCustom {
	private val review = QReview.review
	
	override fun findAllByUserId(userId: String): Flow<Review> {
		return reviewQueryDslRepository.findAll(review.userId.contains(userId)).asFlow()
	}
	
	override fun findAllByItemId(itemId: String): Flow<Review> {
		return reviewQueryDslRepository.findAll(review.itemId.contains(itemId)).asFlow()
	}
	
	override fun findAllByUserAndItemId(userId: String, itemId: String): Flow<Review> {
		val userCondition = review.userId.eq(userId)
		val itemCondition = review.itemId.eq(itemId)
		val combinedCondition = userCondition.and(itemCondition)
		return reviewQueryDslRepository.findAll(combinedCondition).asFlow()
	}
}