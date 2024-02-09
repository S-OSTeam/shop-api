package sosteam.deamhome.global.category.respository

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import sosteam.deamhome.global.category.entity.CategoryEntity
import java.util.UUID

interface CategoryRepository<T: CategoryEntity>: CoroutineCrudRepository<T, Long> {

    suspend fun findByPublicId(publicId: UUID): T?

    suspend fun findByParentPublicIdAndTitle(publicId: UUID, title: String): T?

    fun findByTitle(title: String): Flow<T>
}