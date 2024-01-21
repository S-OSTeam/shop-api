package sosteam.deamhome.domain.review.repository.custom

import kotlinx.coroutines.flow.Flow
import sosteam.deamhome.domain.review.entity.Review

interface ReviewRepositoryCustom {
	fun findAllByUserId(userId: String): Flow<Review>
	fun findAllByItemId(itemId: String): Flow<Review>
}