package sosteam.deamhome.global.category.respository

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import sosteam.deamhome.global.category.entity.Category

interface CategoryRepository<T: Category>: CoroutineCrudRepository<T, Long> {

    suspend fun findByPublicId(publicId: String): T?

    suspend fun findByParentPublicIdAndTitle(publicId: String, title: String): T?

    fun findByTitle(title: String): Flow<T>
}