package sosteam.deamhome.domain.category.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.item.entity.Item


@Repository
interface ItemCategoryRepository : ReactiveMongoRepository<ItemCategory, String> {
    suspend fun findByTitle(name: String): ItemCategory?
}