package sosteam.deamhome.domain.item.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.repository.custom.ItemRepositoryCustom

@GraphQlRepository
interface ItemRepository :ReactiveMongoRepository<Item, String>, ItemRepositoryCustom {
    suspend fun deleteItemById(id: String)
}