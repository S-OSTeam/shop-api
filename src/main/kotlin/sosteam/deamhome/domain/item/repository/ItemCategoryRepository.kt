package sosteam.deamhome.domain.item.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.item.entity.ItemCategory
import sosteam.deamhome.domain.item.repository.custom.ItemCategoryRepositoryCustom
import sosteam.deamhome.global.category.respository.CategoryRepository

@GraphQlRepository
interface ItemCategoryRepository : CategoryRepository<ItemCategory>, ItemCategoryRepositoryCustom {
	suspend fun deleteByPublicId(publicId: String): Long
	
	fun findByParentPublicId(publicId: String): Flow<ItemCategory>
	fun findByParentPublicIdIn(publicIds: List<String>): Flow<ItemCategory>
}