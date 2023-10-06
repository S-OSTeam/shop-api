package sosteam.deamhome.domain.item.service

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.entity.dto.ItemDTO
import sosteam.deamhome.domain.item.repository.ItemCategoryRepository
import sosteam.deamhome.domain.item.repository.ItemDetailCategoryRepository
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.domain.item.resolver.request.ItemCreateRequest
import sosteam.deamhome.global.attribute.Role
import sosteam.deamhome.global.attribute.Status
import sosteam.deamhome.global.entity.Image
import java.time.LocalDateTime

@Service
class ItemService(
    private val itemRepository: ItemRepository,
    private val accountRepository: AccountRepository,
    private val itemCategoryRepository: ItemCategoryRepository,
    private val itemDetailCategoryRepository: ItemDetailCategoryRepository,
    ) {
    suspend fun createItem(request: ItemCreateRequest) : ItemDTO{
//        val account = accountRepository.findById(request.accountId).awaitSingleOrNull()
//            ?: return ItemDTO(
//                title = "no Acount",
//                price = 1010101
//            )
//        val itemCategory = itemCategoryRepository.findById(request.itemCategoryId).awaitSingleOrNull()
//            ?:return ItemDTO(
//                title = "no Category",
//                price = 1010101
//            )
//        val itemDetailCategory = itemDetailCategoryRepository.findById(request.itemDetailCategoryId).awaitSingleOrNull()
//                ?: return ItemDTO(
//                title = "no DetailCategory",
//                price = 1010101
//            )
        val images = request.imageUrls.map { imageUrl ->
            Image(
                fileName = "finleName",
                fileOriginName = "fileOriginName",
                size = 10,
                type = "type",
                fileUrl = imageUrl
            )
        }
        val newItem = Item(
            title = request.title,
            content = request.content,
            summary = request.summary,
            price = request.price,
            sellCnt = request.sellCnt,
            wishCnt = request.wishCnt,
            clickCnt = request.clickCnt,
            avgReview = request.avgReview,
            reviewCnt = request.reviewCnt,
            qnaCnt = request.qnaCnt,
            status = request.status,
            account = account,
            itemCategory = itemCategory,
            itemDetailCategory = itemDetailCategory,
            images = images
        )
        itemRepository.insert(newItem).awaitSingle()
        return ItemDTO(
            title = newItem.title,
            price = newItem.price
        )
    }

    suspend fun findItemByTitle(title: String) : ItemDTO{
        val item = itemRepository.findByTitle(title)
        if (item != null) {
            return ItemDTO(
                title = item.title,
                price = item.price
            )
        }
        return ItemDTO()
    }

    private fun createDefaultAccount(): Account {
        return Account(
            userId = "default_user_id",
            pwd = "default_password",
            sex = false, // 성별 쓰레기 값
            birtyday = LocalDateTime.now(), // 생년월일 쓰레기 값
            zipcode = "default_zipcode",
            address1 = "default_address1",
            address2 = "default_address2",
            address3 = "default_address3",
            address4 = "default_address4",
            email = "default_email",
            receiveMail = false, // 이메일 수신 여부 쓰레기 값
            createdIp = "default_created_ip",
            adminTxt = "default_admin_text",
            snsId = "default_sns_id",
            phone = "default_phone",
            userName = "default_user_name",
            point = 0, // 포인트 쓰레기 값
            role = Role.ROLE_USER,
            status = AccountStatus(
                userId = "default_user_id",
                snsId = "default_sns_id",
                status = Status.LIVE // 쓰레기 값으로 설정
            )
        )
    }

    private fun createDefaultAccountStatus(account: Account): AccountStatus {
        return AccountStatus(
            userId = account.userId,
            snsId = account.snsId,
            status = Status.LIVE // 쓰레기 값으로 설정
        )
    }

}