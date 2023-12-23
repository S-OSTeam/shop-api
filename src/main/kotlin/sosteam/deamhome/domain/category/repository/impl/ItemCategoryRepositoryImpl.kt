package sosteam.deamhome.domain.category.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingleOrNull
import lombok.RequiredArgsConstructor
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.entity.QItemCategory
import sosteam.deamhome.domain.category.repository.custom.ItemCategoryRepositoryCustom
import sosteam.deamhome.domain.category.repository.querydsl.ItemCategoryQueryDslRepository

@GraphQlRepository
@RequiredArgsConstructor
@OptIn(kotlinx.coroutines.FlowPreview::class)
class ItemCategoryRepositoryImpl (
    private val repository: ItemCategoryQueryDslRepository
) : ItemCategoryRepositoryCustom{
    private val itemCategory = QItemCategory.itemCategory

    override fun findItemCategoriesContainTitle(title: String): Flow<ItemCategory> {
        return repository.findAll(itemCategory.title.contains(title)).asFlow()
    }

    override fun findAllItemCategories(): Flow<ItemCategory> {
        return repository.findAll().asFlow()
    }

    override suspend fun findItemCategoryById(id: String): ItemCategory? {
        return repository.findOne(itemCategory.id.eq(id)).awaitSingleOrNull()
    }


}