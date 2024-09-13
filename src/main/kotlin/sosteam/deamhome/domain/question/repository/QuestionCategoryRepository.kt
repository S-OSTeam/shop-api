package sosteam.deamhome.domain.question.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.question.entity.QuestionCategory
import sosteam.deamhome.domain.question.repository.custom.QuestionCategoryRepositoryCustom
import sosteam.deamhome.global.category.respository.CategoryRepository

@GraphQlRepository
interface QuestionCategoryRepository : CategoryRepository<QuestionCategory>, QuestionCategoryRepositoryCustom {
	suspend fun deleteByPublicId(publicId: String): Long
	
	fun findByParentPublicId(publicId: String): Flow<QuestionCategory>
	fun findByParentPublicIdIn(publicIds: List<String>): Flow<QuestionCategory>
}