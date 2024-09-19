package sosteam.deamhome.domain.question.repository.custom

import kotlinx.coroutines.flow.Flow
import sosteam.deamhome.domain.item.handler.request.QuestionFilterRequest
import sosteam.deamhome.domain.question.entity.Question

interface QuestionRepositoryCustom {
	fun findAllQuestionsByFilter(filter: QuestionFilterRequest, questionCategories: List<String>): Flow<Question>
}