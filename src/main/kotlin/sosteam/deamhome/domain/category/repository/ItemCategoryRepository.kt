package sosteam.deamhome.domain.category.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.repository.custom.ItemCategoryRepositoryCustom
import sosteam.deamhome.global.category.respository.CategoryRepository
import java.util.*

@GraphQlRepository
interface ItemCategoryRepository : CategoryRepository<ItemCategory>, ItemCategoryRepositoryCustom{
    suspend fun deleteByPublicId(publicId: UUID): Long

    fun findByParentPublicId(publicId: UUID): Flow<ItemCategory>
    fun findByParentPublicIdIn(publicIds: List<UUID>): Flow<ItemCategory>
}