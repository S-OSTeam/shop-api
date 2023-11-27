package sosteam.deamhome.domain.category.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository

@Service
class CategoryDeleteService ( private val itemCategoryRepository: ItemCategoryRepository ) {
    suspend fun deleteItemCategoryById(id: String): Long{
        return itemCategoryRepository.deleteItemCategoryById(id)
    }

}