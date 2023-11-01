package sosteam.deamhome.domain.category.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.reactive.asFlow
import lombok.RequiredArgsConstructor
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.category.dto.ItemCategoryDTO2
import sosteam.deamhome.domain.category.entity.QItemCategory
import sosteam.deamhome.domain.category.repository.querydsl.ItemCategoryQueryDslRepository
import sosteam.deamhome.domain.category.repository.custom.ItemCategoryRepositoryCustom
import sosteam.deamhome.domain.item.entity.dto.ItemDTO

@GraphQlRepository
@RequiredArgsConstructor
class ItemCategoryRepositoryImpl (
    private val repository: ItemCategoryQueryDslRepository
) : ItemCategoryRepositoryCustom{
    private val itemCategory = QItemCategory.itemCategory

    override fun getItemCategoriesContainsTitle(title: String): Flow<ItemCategoryDTO2> {
        return repository.findAll(itemCategory.title.contains(title)).map { it.toDTO() }.asFlow()
    }

    override fun getItemsContainsTitle(title: String): Flow<ItemDTO> {
        val findAll = repository
            .findAll(itemCategory.itemDetailCategories.any().items.any().title.contains(title))
            .map { it.toItemDTOList().asFlow() }.asFlow().flattenMerge()

        return findAll
    }
}