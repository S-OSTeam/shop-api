package sosteam.deamhome.domain.category.service

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.category.dto.response.ItemCategoryResponseDTO
import sosteam.deamhome.domain.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.exception.DetailCategoryNotFoundException
import sosteam.deamhome.domain.category.exception.SameCategoryException
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository

@Service
class CategoryUpdateService (
    private val itemCategoryRepository: ItemCategoryRepository
) {

    suspend fun modifyDetailCategory(detailCategoryTitle: String, categoryTitle: String): ItemCategoryResponseDTO{
        val oldCategory = itemCategoryRepository.getCategoryByDetailCategoryTitle(detailCategoryTitle)
            ?: throw CategoryNotFoundException()
        val newCategory = itemCategoryRepository.findByTitle(categoryTitle)
            ?: throw CategoryNotFoundException()

        if (oldCategory == newCategory) throw SameCategoryException()

        val find = oldCategory.itemDetailCategories.find { it.title == detailCategoryTitle }
            ?: throw DetailCategoryNotFoundException()
        oldCategory.itemDetailCategories.remove(find)
        newCategory.itemDetailCategories.add(find)

        itemCategoryRepository.save(newCategory).awaitSingleOrNull()
        itemCategoryRepository.save(oldCategory).awaitSingleOrNull()

        return ItemCategoryResponseDTO.fromItemCategory(newCategory)
    }
}