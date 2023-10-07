package sosteam.deamhome.domain.category.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import sosteam.deamhome.domain.category.entity.ItemCategory


@Repository
interface ItemCategoryRepository : ReactiveMongoRepository<ItemCategory, String> {
}