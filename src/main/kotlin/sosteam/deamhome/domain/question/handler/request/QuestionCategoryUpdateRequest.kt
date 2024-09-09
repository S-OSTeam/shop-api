package sosteam.deamhome.domain.item.handler.request

import sosteam.deamhome.domain.question.entity.QuestionType

data class QuestionCategoryUpdateRequest(
	val publicId: String,
	val parentPublicId: String?,
	val questionType: QuestionType,
	val title: String?
)