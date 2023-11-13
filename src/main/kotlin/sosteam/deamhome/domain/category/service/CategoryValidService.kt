package sosteam.deamhome.domain.category.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.category.dto.response.ItemCategoryResponseDTO
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.exception.AlreadyExistCategoryException
import sosteam.deamhome.domain.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository

@Service
class CategoryValidService(private val categoryRepository: ItemCategoryRepository) {

	suspend fun getCategoryByTitle(title: String): ItemCategory? {
		return categoryRepository.findByTitle(title)
	}

	fun getItemCategoriesContainsTitle(title: String): Flow<ItemCategoryResponseDTO> {
		return categoryRepository.getItemCategoriesContainsTitle(title)
			.map { ItemCategoryResponseDTO.fromItemCategory(it) }
	}

	suspend fun isExistCategory(title: String): ItemCategoryResponseDTO{
		return ItemCategoryResponseDTO.fromItemCategory(
			getCategoryByTitle(title)
				?: throw CategoryNotFoundException()
		)
	}

	suspend fun isAlreadyExistCategory(title: String): Boolean{
		getCategoryByTitle(title)?.let { throw AlreadyExistCategoryException() }
		return true
	}
}