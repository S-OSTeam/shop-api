package sosteam.deamhome.domain.category.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.category.dto.response.ItemDetailCategoryResponseDTO
import sosteam.deamhome.domain.category.exception.DetailCategoryNotFoundException
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository

@Service
class DetailCategorySearchService(
    private val itemCategoryRepository: ItemCategoryRepository
) {
    suspend fun getItemDetailCategoryByTitle(title: String): ItemDetailCategoryResponseDTO{
        val itemDetailCategory = itemCategoryRepository.getItemDetailCategoryByTitle(title)
            ?: throw DetailCategoryNotFoundException()
        return ItemDetailCategoryResponseDTO.fromItemDetailCategory(itemDetailCategory)
    }
}