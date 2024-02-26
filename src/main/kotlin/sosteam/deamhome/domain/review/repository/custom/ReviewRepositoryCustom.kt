package sosteam.deamhome.domain.review.repository.custom

import kotlinx.coroutines.flow.Flow
import sosteam.deamhome.domain.review.entity.Review

interface ReviewRepositoryCustom {
	fun findReviews(reviewId: List<Long?>?, userId: List<String?>?, itemId: List<String?>?): Flow<Review>
}