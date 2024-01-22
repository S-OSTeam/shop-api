package sosteam.deamhome.domain.category.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.repository.custom.ItemCategoryRepositoryCustom
import sosteam.deamhome.global.category.respository.CategoryRepository

@GraphQlRepository
interface ItemCategoryRepository : CategoryRepository<ItemCategory>, ItemCategoryRepositoryCustom{
    suspend fun deleteByPublicId(publicId: Long): ItemCategory?

    fun findByParentPublicId(publicId: Long): Flow<ItemCategory>
    fun findByParentPublicIdIn(publicIds: List<Long>): Flow<ItemCategory>

}