package sosteam.deamhome.domain.category.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.category.dto.response.ItemDetailCategoryResponse
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository

@Service
@Transactional
class DetailCategorySearchService(
    private val itemCategoryRepository: ItemCategoryRepository
) {

    //TODO 없으면 에러처리?
    suspend fun getItemDetailCategoryByTitle(title: String): ItemDetailCategoryResponse{
        val itemDetailCategorybyTitle = itemCategoryRepository.getItemDetailCategoryByTitle(title)
            ?:ItemDetailCategoryResponse()
        return itemDetailCategorybyTitle
    }
}