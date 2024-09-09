package sosteam.deamhome.domain.item.handler.response

import sosteam.deamhome.domain.question.handler.response.QuestionResponse

data class QuestionSearchResponse(
	val questions: List<QuestionResponse>,
	val totalCount: Long
)
