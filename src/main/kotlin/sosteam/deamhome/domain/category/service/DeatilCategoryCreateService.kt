package sosteam.deamhome.domain.category.service

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.category.entity.ItemDetailCategory
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.category.dto.request.DetailCategoryRequestDTO
import sosteam.deamhome.domain.category.dto.response.ItemCategoryResponseDTO
import sosteam.deamhome.domain.category.exception.AlreadyExistDetailCategoryException
import sosteam.deamhome.domain.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.exception.CategorySaveFailException

@Service
class DetailCategoryCreateService(
    private val itemCategoryRepository: ItemCategoryRepository
) {
    //Category 안에 잘 들어갔나 확인하기 위해서 ItemCategoryResponseDTO 로 했는데 Detail 로 바꿔야하나?
    suspend fun createDetailCategory(request: DetailCategoryRequestDTO): ItemCategoryResponseDTO {
        val itemCategory = itemCategoryRepository.findByTitle(request.categoryTitle)
            ?: throw CategoryNotFoundException()

        if (itemCategory.itemDetailCategories.any { it.title == request.title }) {
            throw AlreadyExistDetailCategoryException()
        }

        val newItemDetailCategory = ItemDetailCategory(title = request.title)
        itemCategory.modifyDetailCategory((itemCategory.itemDetailCategories + newItemDetailCategory).toMutableList())

        val insertedItemCategory = itemCategoryRepository.save(itemCategory).awaitSingleOrNull()
            ?: throw CategorySaveFailException()

        return ItemCategoryResponseDTO.fromItemCategory(insertedItemCategory)
    }

}