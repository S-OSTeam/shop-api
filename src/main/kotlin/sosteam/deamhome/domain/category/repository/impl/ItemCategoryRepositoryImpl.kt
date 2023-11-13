package sosteam.deamhome.domain.category.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingleOrNull
import lombok.RequiredArgsConstructor
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.entity.ItemDetailCategory
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

    override fun getItemCategoriesContainsTitle(title: String): Flow<ItemCategory> {
        return repository.findAll(itemCategory.title.contains(title)).asFlow()
    }

    override suspend fun getItemDetailCategoryByTitle(title: String): ItemDetailCategory? {
        return repository.findOne(itemCategory.itemDetailCategories.any().title.eq(title))
            .map { result ->
                result.itemDetailCategories.find { it.title == title }
            }
            .awaitSingleOrNull()
    }

    override fun getItemIdsByCategoryTitle(title: String): Flow<String> {
        return repository.findAll(itemCategory.title.eq(title))
            .map { it.itemDetailCategories.flatMap { it.itemIdList }.asFlow() }
            .asFlow().flattenMerge()
    }

    override fun getItemIdsByItemDetailCategoryTitle(title: String): Flow<String> {
        val asFlow = repository.findOne(itemCategory.itemDetailCategories.any().title.eq(title))
            .map {
                it.itemDetailCategories.find { it.title == title }?.itemIdList
            }
            .flatMapIterable { it->it }.asFlow()
        return asFlow
    }


}