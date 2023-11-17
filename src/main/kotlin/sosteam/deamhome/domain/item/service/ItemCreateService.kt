package sosteam.deamhome.domain.item.service

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.exception.CategorySaveFailException
import sosteam.deamhome.domain.category.exception.DetailCategoryNotFoundException
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.domain.item.entity.dto.request.ItemRequestDTO
import sosteam.deamhome.domain.item.entity.dto.response.ItemResponseDTO
import sosteam.deamhome.domain.item.exception.ItemSaveFailException
import sosteam.deamhome.global.image.provider.ImageProvider

@Service
class ItemCreateService(
//    private val accountRepository: AccountRepository,
    private val itemCategoryRepository: ItemCategoryRepository,
    private val itemRepository: ItemRepository,
    private val imageProvider: ImageProvider
    ) {

    //TODO  account find by 로 수정
    //TODO item entity 에 image 가 List<String> 으로 돼있는데 나중에 싹다 바꿔야함
    suspend fun createItem(request: ItemRequestDTO) : ItemResponseDTO {
//        val account = accountRepository.findAccountByUserId(request.sellerId)
//            ?: throw AccountNotFoundException()

        val itemCategory = itemCategoryRepository.findByTitle(request.categoryTitle)
            ?: throw CategoryNotFoundException()

        val itemDetailCategory = itemCategory.itemDetailCategories.find { it.title == request.detailCategoryTitle }
            ?: throw DetailCategoryNotFoundException()

        val images = request.images
        val imageUrls = mutableListOf<String>()

        val innerLocation = request.categoryTitle + request.detailCategoryTitle + request.title

        images.map {
            val ret = imageProvider.saveImage(it, "item", innerLocation).awaitSingle()
            imageUrls.add(ret.fileUrl)
        }

        val item = request.asDomain().apply { this.imageUrls = imageUrls }
        itemDetailCategory.modifyItems((itemDetailCategory.itemIdList + item.id).toMutableList())

        itemCategoryRepository.save(itemCategory).awaitSingleOrNull()
            ?: throw CategorySaveFailException()

        val savedItem = itemRepository.save(item).awaitSingleOrNull()
            ?: throw ItemSaveFailException()
//        에러처리? 지금은 unique index 가 없어서 넣으면 무조건 들어감

        return ItemResponseDTO.fromItem(savedItem)
    }

}