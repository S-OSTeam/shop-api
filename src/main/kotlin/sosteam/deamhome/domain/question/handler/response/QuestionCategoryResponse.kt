package sosteam.deamhome.domain.item.handler.response

import sosteam.deamhome.domain.question.entity.QuestionCategory

class QuestionCategoryResponse(
	val title: String,
	val publicId: String,
	val parentPublicId: String
) {
	companion object {
		fun fromQuestionCategory(questionCategory: QuestionCategory): QuestionCategoryResponse {
			return QuestionCategoryResponse(
				title = questionCategory.title,
				publicId = questionCategory.publicId,
				parentPublicId = questionCategory.parentPublicId
			)
		}
	}
}