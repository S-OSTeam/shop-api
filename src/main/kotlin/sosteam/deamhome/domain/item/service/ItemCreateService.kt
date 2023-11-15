package sosteam.deamhome.domain.item.service

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

@Service
class ItemCreateService(
//    private val accountRepository: AccountRepository,
    private val itemCategoryRepository: ItemCategoryRepository,
    private val itemRepository: ItemRepository
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

        val item = request.asDomain()
        itemDetailCategory.modifyItems((itemDetailCategory.itemIdList + item.id).toMutableList())

        itemCategoryRepository.save(itemCategory).awaitSingleOrNull()
            ?: throw CategorySaveFailException()

        val savedItem = itemRepository.save(item).awaitSingleOrNull()
            ?: throw ItemSaveFailException()
//        에러처리? 지금은 unique index 가 없어서 넣으면 무조건 들어감

        return ItemResponseDTO.fromItem(savedItem)
    }

}