package sosteam.deamhome.domain.item.handler.request

import sosteam.deamhome.domain.question.entity.QuestionStatus
import sosteam.deamhome.domain.question.entity.QuestionType
import sosteam.deamhome.global.attribute.PostType

data class QuestionFilterRequest(
	val questionFilter: QuestionFilter?,
	val questionCategoryFilter: QuestionCategoryFilter?,
	val storeId: String,
	val questionType: QuestionType,
	// 페이지 번호
	val pageNumber: Long = 1,
	// 페이지 크기
	val pageSize: Long = 10
)

data class QuestionFilter(
	val title: String?,
	val content: String?,
	val postId: String?,
	val itemId: String?,
	val publicId: String?,
	val postType: List<PostType>?,
	val questionStatus: List<QuestionStatus>,
	val questionIsCompleted: Boolean?,
)

data class QuestionCategoryFilter(
	val title: String?,
	val publicId: String?,
)