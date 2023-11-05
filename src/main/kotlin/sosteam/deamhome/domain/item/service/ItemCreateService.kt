package sosteam.deamhome.domain.item.service

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.entity.ItemDetailCategory
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.entity.dto.ItemDTO
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.domain.item.entity.dto.request.ItemCreateRequest
import sosteam.deamhome.global.attribute.Role
import sosteam.deamhome.global.attribute.SNS
import java.time.LocalDateTime

@Service
@Transactional
class ItemCreateService(
    private val accountRepository: AccountRepository,
    private val itemCategoryRepository: ItemCategoryRepository,
    private val itemRepository: ItemRepository
    ) {

    //TODO  account find by 로 수정
    //TODO account, itemCategory, itemDetailCategory findby 해서 없으면 만들지 말고 에러처리?? 아니면 생기는게 맞나?
    //TODO item entity 에 image 가 List<String> 으로 돼있는데 나중에 싹다 바꿔야함
    suspend fun createItem(request: ItemCreateRequest) : ItemDTO {
        val minho = createDefaultAccount()
        val itemCategory = itemCategoryRepository.findByTitle(request.categoryTitle) ?: ItemCategory(title = request.categoryTitle)
        val itemDetailCategories = itemCategory.itemDetailCategories

        var existingItemDetailCategory = itemDetailCategories.find { it.title == request.detailCategoryTitle }

        if (existingItemDetailCategory == null) {
            existingItemDetailCategory = ItemDetailCategory(title = request.detailCategoryTitle)
            itemDetailCategories.add(existingItemDetailCategory)
            itemCategory.modifyDetailCategory(itemDetailCategories)
        }

        val items = existingItemDetailCategory.itemIdList
        val item = Item.fromRequest(request, minho)

        val insert = itemRepository.save(item).awaitSingleOrNull()
//        에러처리? 지금은 unique index 가 없어서 넣으면 무조건 들어감
//            ?: null
        val itemDTO = insert!!.toItemDTO()

        items.add(item.id)
        println(item.id)

        val inserted = itemCategoryRepository.save(itemCategory).awaitSingleOrNull()

        return itemDTO
    }

    fun createDefaultAccount(): Account {
        return Account(
            userId = "alsgh",
            pwd = "1234",
            sex = false,
            birtyday = LocalDateTime.now(),
            zipcode = "",
            address1 = "",
            address2 = "",
            address3 = "",
            address4 = "",
            email = "alsgh846@gmail.com",
            receiveMail = false,
            createdIp = "",
            adminTxt = "",
            snsId = null,
            sns = SNS.NORMAL,
            phone = "01042412447",
            userName = "jung min ho",
            point = 0,
            role = Role.ROLE_GUEST
        )
    }

}