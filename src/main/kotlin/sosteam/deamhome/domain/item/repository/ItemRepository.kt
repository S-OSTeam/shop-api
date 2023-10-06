package sosteam.deamhome.domain.item.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.item.entity.Item


@Repository
interface ItemRepository : ReactiveMongoRepository<Item, String> {
    suspend fun findByTitle(name: String): Item?
}