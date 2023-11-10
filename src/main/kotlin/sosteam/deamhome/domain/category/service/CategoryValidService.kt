package sosteam.deamhome.domain.category.service

import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.exception.AccountNotFoundException
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository

@Service
@RequiredArgsConstructor
class CategoryValidService(private val categoryRepository: ItemCategoryRepository) {

	suspend fun getCategoryByTitle(title: String): ItemCategory? {
		return categoryRepository.findByTitle(title) ?: throw CategoryNotFoundException()
	}
}