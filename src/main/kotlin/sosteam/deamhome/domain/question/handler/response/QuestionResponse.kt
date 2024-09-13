package sosteam.deamhome.domain.question.handler.response

import sosteam.deamhome.domain.question.entity.Question
import sosteam.deamhome.domain.question.entity.QuestionStatus
import sosteam.deamhome.domain.question.entity.QuestionType

class QuestionResponse(
	val publicId: String,
	val categoryPublicId: String,
	val title: String,
	val content: String,
	val summary: String?,
	val questionType: QuestionType,
	val questionIsCompleted: Boolean,
	val questionStatus: QuestionStatus,
	val postId: String?,
	val userId: String,
	val storeId: String?,
	val imageUrls: List<String>,
) {
	companion object {
		fun fromQuestion(question: Question): QuestionResponse {
			return QuestionResponse(
				publicId = question.publicId,
				categoryPublicId = question.categoryPublicId,
				title = question.title,
				content = question.content,
				summary = question.summary,
				questionType = question.questionType,
				questionIsCompleted = question.questionIsCompleted,
				questionStatus = question.questionStatus,
				storeId = question.storeId,
				imageUrls = question.imageUrls,
				postId = question.postId,
				userId = question.userId
			)
		}
	}
}