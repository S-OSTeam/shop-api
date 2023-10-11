package sosteam.deamhome.domain.item.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor
import org.springframework.stereotype.Repository
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
    suspend fun findByTitleWithCategory(title: String): ItemDTO?

    @Aggregation(
        "{ \$lookup: { from: 'itemCategory', localField: 'itemCategory', foreignField: '_id', as: 'category' } }",
        "{ \$unwind: '\$category' }",
        "{ \$project: { title: '\$title', price:'\$price', category: '\$category.title' } }"
    )
    fun findAllWithCategory(): Flow<ItemDTO>


}