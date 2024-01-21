package sosteam.deamhome.domain.category.repository

import com.querydsl.sql.PostgreSQLTemplates
import com.querydsl.sql.postgresql.PostgreSQLQuery
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.repository.custom.ItemCategoryRepositoryCustom

@GraphQlRepository
interface ItemCategoryRepository : CoroutineCrudRepository<ItemCategory, Long>, ItemCategoryRepositoryCustom {
    suspend fun findByPublicId(publicId: String): ItemCategory?
    suspend fun deleteByPublicId(publicId: String): Long?

    suspend fun findByParentPublicIdAndTitle(publicId: String, title: String): ItemCategory?

    fun findByParentPublicId(publicId: String): Flow<ItemCategory>
    fun findByParentPublicIdIn(publicIds: List<String>): Flow<ItemCategory>

    fun findByTitle(title: String): Flow<ItemCategory>

}