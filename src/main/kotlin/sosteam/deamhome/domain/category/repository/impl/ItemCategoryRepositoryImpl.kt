package sosteam.deamhome.domain.category.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import lombok.RequiredArgsConstructor
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.category.dto.ItemCategoryDTO2
import sosteam.deamhome.domain.category.entity.QItemCategory
import sosteam.deamhome.domain.category.repository.querydsl.ItemCategoryQueryDslRepository
import sosteam.deamhome.domain.category.repository.custom.ItemCategoryRepositoryCustom

@GraphQlRepository
@RequiredArgsConstructor
class ItemCategoryRepositoryImpl (
    private val repository: ItemCategoryQueryDslRepository
) : ItemCategoryRepositoryCustom{
    private val itemCategory = QItemCategory.itemCategory

    override fun getItemsContainsTitle(title: String): Flow<ItemCategoryDTO2> {
        val findAll = repository.findAll(itemCategory.title.contains(title)).map { it.toDTO() }.asFlow()
        return findAll
    }
}