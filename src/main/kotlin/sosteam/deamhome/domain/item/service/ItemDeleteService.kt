package sosteam.deamhome.domain.item.service

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.item.exception.ItemNotFoundException
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.global.image.provider.ImageProvider

@Service
class ItemDeleteService (
    private val itemRepository: ItemRepository,
    private val itemCategoryRepository: ItemCategoryRepository,
    private val imageProvider: ImageProvider
) {

    suspend fun deleteItem(id: String) : String {
        val category = itemCategoryRepository.findCategoryByItemId(id)
            ?: throw CategoryNotFoundException()

        category.itemDetailCategories.forEach { detailCategory ->
            if (id in detailCategory.itemIdList) {
                detailCategory.itemIdList.remove(id)
                return@forEach
            }
        }

        val item = itemRepository.findById(id).awaitSingleOrNull()
            ?: throw ItemNotFoundException()

        // 근데 이미지는 지워지는데 폴더는 그대로 있는데 어떡하지
        item.images.map {
            imageProvider.deleteImage(it.path)
        }

        itemCategoryRepository.save(category).awaitSingleOrNull()
        itemRepository.deleteItemById(id)

        return id
    }

}