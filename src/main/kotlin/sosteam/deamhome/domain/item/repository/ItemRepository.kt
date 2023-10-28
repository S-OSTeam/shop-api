package sosteam.deamhome.domain.item.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.entity.dto.ItemDTO


@Repository
interface ItemRepository : ReactiveMongoRepository<Item, String>, ReactiveQuerydslPredicateExecutor<Item>{

    @Aggregation(
        "{\$match: { title: ?0} }",
        "{\$lookup: { from: 'itemCategory',localField: 'itemCategory',foreignField: '_id',as: 'part'} }",
        "{\$unwind: '\$part' }",
        "{\$project: { title: '\$title',price: '\$price',category: '\$part.title'} }"
    )
    suspend fun findByTitleWithCategory22(title: String): ItemDTO?

    @Aggregation(
        "{ \$lookup: { from: 'itemCategory', localField: 'itemCategory', foreignField: '_id', as: 'category' } }",
        "{ \$unwind: '\$category' }",
        "{ \$project: { title: '\$title', price:'\$price', category: '\$category.title' } }"
    )
    fun findAllWithCategory(): Flow<ItemDTO>

//    fun findAllFlow(): Flow<Item>{
//        return findAll().asFlow()
//    }
}