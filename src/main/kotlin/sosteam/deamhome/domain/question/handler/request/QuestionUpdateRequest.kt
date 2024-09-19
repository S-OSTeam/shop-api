package sosteam.deamhome.domain.item.handler.request

import sosteam.deamhome.domain.question.entity.QuestionStatus
import sosteam.deamhome.domain.question.entity.QuestionType

data class QuestionUpdateRequest(
	val publicId: String,
	val categoryPublicId: String?,
	val title: String?,
	val content: String?,
	val summary: String?,
	val userId: String?,
	val postId: String?,
	val itemId: String?,
	val questionStatus: QuestionStatus?,
	val questionType: QuestionType?,
	val questionIsCompleted: Boolean?,
	val storeId: String?,
	val imageUrls: List<String>?
)