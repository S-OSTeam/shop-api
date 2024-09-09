package sosteam.deamhome.domain.item.handler.request

import com.github.f4b6a3.ulid.UlidCreator
import sosteam.deamhome.domain.question.entity.QuestionCategory
import sosteam.deamhome.domain.question.entity.QuestionType
import sosteam.deamhome.global.entity.DTO

data class QuestionCategoryRequest(
	
	val title: String,
	val questionType: QuestionType,
	val storeId: String,
	val parentPublicId: String?
) : DTO {
	override fun asDomain(): QuestionCategory {
		val publicId = UlidCreator.getMonotonicUlid().toString().replace("-", "")
		return QuestionCategory(
			// id 는 save 하고 postgreSQL bigSerial 으로 자동 생성
			id = null,
			title = this.title,
			publicId = publicId,
			questionType = this.questionType,
			storeId = this.storeId,
			// request 에서 parentPublicId 가 null 로 들어오면 최상위 카테고리이므로 자기 자신의 publicId 로 설정함
			parentPublicId = this.parentPublicId ?: publicId
		)
	}
}