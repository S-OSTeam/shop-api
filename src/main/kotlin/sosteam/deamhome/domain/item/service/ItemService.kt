package sosteam.deamhome.domain.item.service


import kotlinx.coroutines.flow.*
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.entity.ItemDetailCategory
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.category.repository.ItemDetailCategoryRepository
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.entity.QItem
import sosteam.deamhome.domain.item.entity.dto.ItemDTO
import sosteam.deamhome.domain.item.repository.ImageRepository
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.domain.item.resolver.request.ItemCreateRequest
import sosteam.deamhome.global.attribute.Role
import sosteam.deamhome.global.attribute.SNS
import sosteam.deamhome.global.entity.Image
import java.time.LocalDateTime

@Service
class ItemService(
    private val itemRepository: ItemRepository,
    private val accountRepository: AccountRepository,
    private val itemCategoryRepository: ItemCategoryRepository,
    private val itemDetailCategoryRepository: ItemDetailCategoryRepository,
    private val imageRepository: ImageRepository
    ) {
    suspend fun createItem(request: ItemCreateRequest) : ItemDTO{
        //TODO findby 를 id 말고 title, userid 로 바꿔야함, 찾았는데 없으면 에러처리 혹은 새로 생성해서 inser 해야함, image 바꿔야함
        val account = accountRepository.findById(request.userId).awaitSingleOrNull()
            ?: createDefaultAccount()
        val itemDetailCategory = itemDetailCategoryRepository.findById("123").awaitSingleOrNull()
            ?: ItemDetailCategory(title = "notFounddetail")
        val itemCategory = itemCategoryRepository.findById("123").awaitSingleOrNull()
            ?: ItemCategory(
                title = "notFoundcategory",
                itemDetailCategory = itemDetailCategory
            )
        itemDetailCategory.modifyCategories(listOf(itemCategory))

        val images = request.imageId.map { id ->
            imageRepository.findById(id).awaitSingleOrNull()
                ?: Image(
                    fileName = "fn",
                    fileOriginName = "fon",
                    size = 123,
                    type = "ty",
                    fileUrl = "fu"
                )
        }
        itemDetailCategoryRepository.insert(itemDetailCategory).awaitSingle()
        itemCategoryRepository.insert(itemCategory).awaitSingle()
        accountRepository.insert(account).awaitSingle()
        images.forEach { image ->
            imageRepository.insert(image).awaitSingle()
        }
//        itemDetailCategoryRepository.insert(itemDetailCategory).awaitSingle()

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

    suspend fun findItemByTitle(title: String) : ItemDTO {
        val all = itemRepository.findAllWithCategory().toList()
        val one = itemRepository.findByTitleWithCategory(title)

        for(item in all){
            println(item.title)
            println(item.price)
            println(item.category)
        }

        if (one != null) {
            println(one.title)
            println(one.price)
            println(one.category)
        }

        val qItem = QItem.item
        val itemPredicate = qItem.title.eq(title)
        val item = itemRepository.findOne(itemPredicate).awaitSingleOrNull()
        println(item?.title)
        println(item?.itemCategory)

        return ItemDTO(
            title = "asdfasdf",
            price = 123123,
            category = "3333"
        )
    }

    fun createDefaultAccount(): Account {
        return Account(
            userId = "defulatid",
            pwd = "", // 여기에 기본 비밀번호를 설정할 수 있습니다.
            sex = false,
            birtyday = LocalDateTime.now(),
            zipcode = "",
            address1 = "",
            address2 = "",
            address3 = "",
            address4 = "",
            email = "", // 여기에 기본 이메일 주소를 설정할 수 있습니다.
            receiveMail = false,
            createdIp = "",
            adminTxt = "",
            snsId = null,
            sns = SNS.NORMAL,
            phone = "", // 여기에 기본 전화번호를 설정할 수 있습니다.
            userName = "", // 여기에 기본 사용자 이름을 설정할 수 있습니다.
            point = 0,
            role = Role.ROLE_GUEST
        )
    }

}