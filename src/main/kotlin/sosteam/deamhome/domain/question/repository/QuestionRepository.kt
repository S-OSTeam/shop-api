package sosteam.deamhome.domain.question.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.question.entity.Question
import sosteam.deamhome.domain.question.repository.custom.QuestionRepositoryCustom

@GraphQlRepository
interface QuestionRepository : CoroutineCrudRepository<Question, Long>, QuestionRepositoryCustom {
	
	suspend fun findByPublicId(publicId: String): Question?
	
	suspend fun deleteByPublicId(publicId: String): Long
	
	fun findByCategoryPublicIdIn(publicIds: List<String>): Flow<Question>
	
	fun findByPublicIdIn(publicIds: List<String>, pageRequest: PageRequest): Flow<Question>
	
	fun findByPublicIdIn(publicIds: List<String>): Flow<Question>
	
	fun findByCategoryPublicId(categoryPublicId: String): Flow<Question>
	
	fun findByStoreId(storeId: String): Flow<Question>
}