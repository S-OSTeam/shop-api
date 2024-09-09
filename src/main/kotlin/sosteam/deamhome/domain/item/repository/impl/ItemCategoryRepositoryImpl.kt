package sosteam.deamhome.domain.item.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.item.entity.ItemCategory
import sosteam.deamhome.domain.category.entity.QItemCategory
import sosteam.deamhome.domain.item.repository.custom.ItemCategoryRepositoryCustom
import sosteam.deamhome.domain.item.repository.querydsl.ItemCategoryQueryDslRepository

@GraphQlRepository
class ItemCategoryRepositoryImpl(
	private val repository: ItemCategoryQueryDslRepository
) : ItemCategoryRepositoryCustom {
	
	private val itemCategory = QItemCategory.itemCategory
	
	override fun findItemCategoriesContainTitle(title: String): Flow<ItemCategory> {
		return repository.findAll(itemCategory.title.contains(title)).asFlow()
	}
	
	override fun findAllItemCategoriesByTitle(title: String): Flow<ItemCategory> {
		return repository.findAll(itemCategory.title.eq(title)).asFlow()
	}
	
	override suspend fun findEqualsTitle(title: String): ItemCategory? {
		return repository.findOne(itemCategory.title.eq(title)).awaitSingleOrNull()
	}
	
}