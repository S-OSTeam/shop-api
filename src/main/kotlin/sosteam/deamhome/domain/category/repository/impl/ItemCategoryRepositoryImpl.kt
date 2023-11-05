package sosteam.deamhome.domain.category.repository.impl

import kotlinx.coroutines.flow.Flow
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

}