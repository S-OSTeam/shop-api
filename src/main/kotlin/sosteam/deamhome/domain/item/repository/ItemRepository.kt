package sosteam.deamhome.domain.item.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.repository.custom.ItemRepositoryCustom
import java.util.*

@GraphQlRepository
interface ItemRepository : CoroutineCrudRepository<Item, Long>, ItemRepositoryCustom {

	suspend fun findByPublicId(publicId: UUID): Item?

	suspend fun deleteByPublicId(publicId: UUID): Long

	fun findByCategoryPublicIdIn(publicIds: List<UUID>): Flow<Item>

	fun findByIdIn(ids: List<String>, pageRequest: PageRequest): Flow<Item>
	suspend fun findItemById(id: String): Item?

}