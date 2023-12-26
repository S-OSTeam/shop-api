package sosteam.deamhome.domain.category.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.repository.custom.ItemCategoryRepositoryCustom

@GraphQlRepository
interface ItemCategoryRepository : ReactiveMongoRepository<ItemCategory, String>, ItemCategoryRepositoryCustom{
    suspend fun findByPublicId(publicId: Long): ItemCategory?

    suspend fun findByTitle(title: String): ItemCategory?
    suspend fun deleteByPublicId(publicId: Long): ItemCategory?

}