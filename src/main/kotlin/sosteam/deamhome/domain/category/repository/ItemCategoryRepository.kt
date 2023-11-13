package sosteam.deamhome.domain.category.repository

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.graphql.data.GraphQlRepository
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.repository.custom.ItemCategoryRepositoryCustom


@GraphQlRepository
interface ItemCategoryRepository : ReactiveMongoRepository<ItemCategory, String>, ItemCategoryRepositoryCustom {
    suspend fun findByTitle(title: String): ItemCategory?

    suspend fun deleteItemCategoryById(id: String)

}