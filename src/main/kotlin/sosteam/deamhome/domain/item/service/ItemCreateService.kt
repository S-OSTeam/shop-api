package sosteam.deamhome.domain.item.service

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.category.DTO.ItemCategoryDTO
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.entity.ItemDetailCategory
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.resolver.request.ItemCreateRequest
import sosteam.deamhome.global.attribute.Role
import sosteam.deamhome.global.attribute.SNS
import java.time.LocalDateTime

@Service
class ItemCreateService(
    private val accountRepository: AccountRepository,
    private val itemCategoryRepository: ItemCategoryRepository
    ) {
    @Transactional
    suspend fun createItem(request: ItemCreateRequest) : ItemCategoryDTO{
        val minho = createDefaultAccount()
        val itemCategory = itemCategoryRepository.findByTitle(request.categoryTitle) ?: ItemCategory(title = request.categoryTitle)
        val itemDetailCategories = itemCategory.itemDetailCategories

        var existingItemDetailCategory = itemDetailCategories.find { it.title == request.detailCategoryTitle }

        if (existingItemDetailCategory == null) {
            existingItemDetailCategory = ItemDetailCategory(title = request.detailCategoryTitle)
            itemDetailCategories.add(existingItemDetailCategory)
            itemCategory.modifyDetailCategory(itemDetailCategories)
        }

        val items = existingItemDetailCategory.items
        val item = Item.fromRequest(request, minho)
        items.add(item)

        val inserted = itemCategoryRepository.save(itemCategory).awaitSingleOrNull()
        return ItemCategoryDTO(title = inserted?.title)
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