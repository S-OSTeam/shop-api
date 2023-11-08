package sosteam.deamhome.domain.category.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingleOrNull
import lombok.RequiredArgsConstructor
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.category.dto.ItemCategoryDTO2
import sosteam.deamhome.domain.category.dto.response.ItemDetailCategoryResponse
import sosteam.deamhome.domain.category.entity.QItemCategory
import sosteam.deamhome.domain.category.repository.custom.ItemCategoryRepositoryCustom
import sosteam.deamhome.domain.category.repository.querydsl.ItemCategoryQueryDslRepository

@GraphQlRepository
@RequiredArgsConstructor
class ItemCategoryRepositoryImpl (
    private val repository: ItemCategoryQueryDslRepository
) : ItemCategoryRepositoryCustom{
    private val itemCategory = QItemCategory.itemCategory

    override fun getItemCategoriesContainsTitle(title: String): Flow<ItemCategoryDTO2> {
        return repository.findAll(itemCategory.title.contains(title)).map { it.toDTO() }.asFlow()
    }

    override suspend fun getItemDetailCategoryByTitle(title: String): ItemDetailCategoryResponse? {
        val findOne = repository.findOne(itemCategory.itemDetailCategories.any().title.eq(title))
            .map {
                it.itemDetailCategories.find { it.title == title }
                    ?.toResponse()
            }
            .awaitSingleOrNull()
        return findOne

    }

    override fun getItemIdsByCategoryTitle(title: String): Flow<String> {
        //결과 없으면 null.asFlow 인데 괜찮나?
        val asFlow = repository.findAll(itemCategory.title.eq(title))
            .map { it.itemDetailCategories.flatMap { it.itemIdList }.asFlow() }
            .asFlow().flattenMerge()
        return asFlow
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