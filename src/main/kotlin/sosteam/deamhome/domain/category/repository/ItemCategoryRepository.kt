package sosteam.deamhome.domain.category.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.repository.custom.ItemCategoryRepositoryCustom

@GraphQlRepository
interface ItemCategoryRepository : ReactiveMongoRepository<ItemCategory, String>, ItemCategoryRepositoryCustom{
    suspend fun findBySequence(sequence: Long): ItemCategory?
    suspend fun deleteBySequence(sequence: Long): ItemCategory?

}