package sosteam.deamhome.domain.item.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.handler.request.ItemRequest
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.global.attribute.ItemStatus
import sosteam.deamhome.global.category.exception.CategoryNotFoundException
import sosteam.deamhome.global.image.provider.ImageProvider
import java.time.OffsetDateTime

class ItemCreateServiceTest : BehaviorSpec({

    val itemRepository = mockk<ItemRepository>()
    val itemCategoryRepository = mockk<ItemCategoryRepository>()
    val imageProvider = mockk<ImageProvider>()
    val itemCreateService = ItemCreateService(itemRepository, itemCategoryRepository, imageProvider)

    Given("a valid item request") {
        val request = ItemRequest(
            categoryPublicId = "testPublicId",
            title = "Test Item",
            content = "",
            summary = "",
            price = 10000,
            status = ItemStatus.AVAILABLE,
            sellerId = "seller123",
            stockCnt = 200,
            freeDelivery = false,
            option = arrayListOf(),
            productNumber = "12345",
            deadline = OffsetDateTime.now(),
            originalWork = "",
            material = "",
            size = "",
            weight = "",
            shippingCost = 0,
            imageUrls = emptyList()
        )

        val mockCategory = ItemCategory(id = 1L, title = "Test Category", publicId = "testPublicId", parentPublicId = "testPublicId")
        val mockSavedItem = Item(
            id = 1L,
            publicId = "",
            categoryPublicId = request.categoryPublicId,
            title = request.title,
            content = request.content,
            summary = request.summary,
            price = request.price,
            sellCnt = 0,
            wishCnt = 0,
            clickCnt = 0,
            avgReview = 0.0,
            reviewCnt = 0,
            qnaCnt = 0,
            status = request.status,
            sellerId = request.sellerId,
            freeDelivery = request.freeDelivery,
            stockCnt = 0,
            reviewScore = emptyList(),
            option = emptyList(),
            productNumber = request.productNumber,
            deadline = request.deadline,
            originalWork = request.originalWork,
            material = request.material,
            size = request.size,
            weight = request.weight,
            shippingCost = request.shippingCost
        ).apply { imageUrls = emptyList() }

        When("creating an item") {
            coEvery { itemCategoryRepository.findByPublicId(request.categoryPublicId) } returns mockCategory
            coEvery { itemRepository.save(any()) } returns mockSavedItem

            val result = itemCreateService.createItem(request)

            Then("it should return the corresponding ItemResponse") {
                result.title shouldBe "Test Item"
            }
        }

        When("creating an item with non-existent category") {
            coEvery { itemCategoryRepository.findByPublicId(request.categoryPublicId) } returns null
            val exception = shouldThrow<CategoryNotFoundException> {
                itemCreateService.createItem(request)
            }

            Then("it should throw CategoryNotFoundException") {
                exception.extensions["code"] shouldBe "CATEGORY_NOT_FOUND"
            }
        }
    }
})
