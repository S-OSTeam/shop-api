package sosteam.deamhome.domain.item.handler.request

import com.github.f4b6a3.ulid.UlidCreator
import sosteam.deamhome.domain.question.entity.Question
import sosteam.deamhome.domain.question.entity.QuestionStatus
import sosteam.deamhome.domain.question.entity.QuestionType
import sosteam.deamhome.global.entity.DTO

data class QuestionRequest(
	val categoryPublicId: String,
	val title: String,
	val content: String,
	val summary: String,
	val userId: String,
	val postId: String?,
	val questionStatus: QuestionStatus,
	val questionType: QuestionType,
	val questionIsCompleted: Boolean,
	val storeId: String,
	val imageUrls: List<String>
) : DTO {
	override fun asDomain(): Question {
		val publicId = UlidCreator.getMonotonicUlid().toString().replace("-", "")
		return Question(
			// id 는 save 하고 postgreSQL bigSerial 으로 자동 생성
			id = null,
			publicId = publicId,
			categoryPublicId = this.categoryPublicId,
			title = this.title,
			content = this.content,
			summary = this.summary,
			questionType = this.questionType,
			questionIsCompleted = this.questionIsCompleted,
			questionStatus = this.questionStatus,
			storeId = this.storeId,
			imageUrls = this.imageUrls,
			postId = this.postId,
			userId = this.userId
		)
	}
}