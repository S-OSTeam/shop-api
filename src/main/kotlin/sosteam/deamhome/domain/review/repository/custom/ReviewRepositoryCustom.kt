package sosteam.deamhome.domain.review.repository.custom

import kotlinx.coroutines.flow.Flow
import sosteam.deamhome.domain.review.entity.Review

interface ReviewRepositoryCustom {
	fun findReviews(reviewId: String?, userId: String?, itemId: String?): Flow<Review>
}