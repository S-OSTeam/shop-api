package sosteam.deamhome.domain.question.handler.response

import sosteam.deamhome.domain.question.entity.QuestionCategory
import sosteam.deamhome.global.category.handler.response.CategoryTreeResponse

class QuestionCategoryResponse(
	override val publicId: String,
	override val title: String,
	override val children: MutableList<CategoryTreeResponse<QuestionCategory>> = mutableListOf()
) : CategoryTreeResponse<QuestionCategory>() {
	companion object {
		fun fromQuestionCategory(questionCategory: QuestionCategory): QuestionCategoryResponse {
			return QuestionCategoryResponse(
				publicId = questionCategory.publicId,
				title = questionCategory.title,
				children = mutableListOf()
			)
		}
	}
}