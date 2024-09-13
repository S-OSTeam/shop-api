package sosteam.deamhome.global.category.factory

import org.springframework.stereotype.Component
import sosteam.deamhome.domain.item.entity.ItemCategory
import sosteam.deamhome.domain.item.repository.ItemCategoryRepository
import sosteam.deamhome.domain.question.entity.QuestionCategory
import sosteam.deamhome.domain.question.repository.QuestionCategoryRepository
import sosteam.deamhome.global.category.entity.CategoryEntity
import sosteam.deamhome.global.category.exception.CategoryNotFoundException
import sosteam.deamhome.global.category.exception.CategoryNotSupportedException
import sosteam.deamhome.global.category.respository.CategoryRepository

@Component
class CategoryProviderFactory(
        private val itemCategoryRepository: ItemCategoryRepository,
        private val questionCategoryRepository: QuestionCategoryRepository
) {
    fun <T : CategoryEntity> getRepository(clazz: Class<T>): CategoryRepository<T> {
        return when (clazz) {
            ItemCategory::class.java -> itemCategoryRepository as CategoryRepository<T>
            QuestionCategory::class.java -> questionCategoryRepository as CategoryRepository<T>
            else -> throw CategoryNotSupportedException()
        }
    }
}
