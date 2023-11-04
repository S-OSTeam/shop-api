package sosteam.deamhome.domain.category.repository.impl

import com.querydsl.mongodb.morphia.MorphiaQuery
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
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.entity.QItem
import sosteam.deamhome.domain.item.entity.dto.ItemDTO
import sosteam.deamhome.global.provider.log

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
        log().info(findOne.toString())
        println("hihihihi")
        return findOne


    }

//    override fun getItemsContainsTitle(title: String): Flow<ItemDTO> {
//        val findAll = repository
//            .findAll(itemCategory.itemDetailCategories.any().items.any().title.contains(title))
//            .map {
//                it.toItemDTOList().asFlow()
//            }.asFlow()
//            .flattenMerge()
//
//
//        return findAll
//    }
}