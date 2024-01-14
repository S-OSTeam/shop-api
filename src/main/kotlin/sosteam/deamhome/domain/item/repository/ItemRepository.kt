package sosteam.deamhome.domain.item.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.repository.custom.ItemRepositoryCustom

@GraphQlRepository
interface ItemRepository : ReactiveMongoRepository<Item, String>, ItemRepositoryCustom {
	
	suspend fun findByPublicId(publicId: Long): Item?
	
	suspend fun deleteByPublicId(publicId: Long): Item?
	
	fun findByCategoryPublicIdIn(publicIds: List<Long>): Flow<Item>
	
	fun findByIdIn(ids: List<String>, pageRequest: PageRequest): Flow<Item>
	suspend fun findItemById(id: String): Item?

	fun findAllBy(pageRequest: PageRequest): Flow<Item>
}