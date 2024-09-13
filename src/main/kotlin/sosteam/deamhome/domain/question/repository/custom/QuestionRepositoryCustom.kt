package sosteam.deamhome.domain.question.repository.custom

import kotlinx.coroutines.flow.Flow
import sosteam.deamhome.domain.item.handler.request.QuestionSearchRequest
import sosteam.deamhome.domain.question.entity.Question

interface QuestionRepositoryCustom {
	fun searchQuestion(request: QuestionSearchRequest): Flow<Question>
}