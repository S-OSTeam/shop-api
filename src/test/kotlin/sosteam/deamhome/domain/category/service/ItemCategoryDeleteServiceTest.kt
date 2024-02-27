package sosteam.deamhome.domain.category.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.global.category.exception.CategoryDeleteFailException
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.global.attribute.ItemStatus
import sosteam.deamhome.global.category.exception.CategoryNotFoundException
import java.time.OffsetDateTime

class ItemCategoryDeleteServiceTest : BehaviorSpec({

    val itemCategoryRepository = mockk<ItemCategoryRepository>()
    val itemRepository = mockk<ItemRepository>()
    val service = ItemCategoryDeleteService(itemCategoryRepository, itemRepository)

    Given("a valid publicId") {

        val category = ItemCategory(
            id = 1L,
            title = "Test Category",
            publicId = "testPublicId",
            parentPublicId = "testParentPublicId"
        )
        val publicId = category.publicId
        val subCategory = ItemCategory(
            id = 2L,
            title = "Test Subcategory",
            publicId = "testSubPublicId",
            parentPublicId = publicId
        )
        val item = Item(
            id = 1L,
            publicId = "",
            categoryPublicId = publicId,
            title = "Test Item",
            content = "",
            summary = "",
            price = 10000,
            sellCnt = 0,
            wishCnt = 0,
            clickCnt = 0,
            avgReview = 0.0,
            reviewCnt = 0,
            qnaCnt = 0,
            status = ItemStatus.AVAILABLE,
            sellerId = "seller123",
            freeDelivery = false,
            stockCnt = 0,
            reviewScore = emptyList(),
            option = emptyList(),
            productNumber = "12345",
            deadline = OffsetDateTime.now(),
            originalWork = "",
            material = "",
            size = "",
            weight = "",
            shippingCost = 0
        ).apply { imageUrls = emptyList() }

        When("deleting a non-existent category") {

            coEvery { itemCategoryRepository.findByPublicId(publicId) } returns null

            Then("it should throw CategoryNotFoundException") {
                shouldThrow<CategoryNotFoundException> {
                    service.deleteItemCategoryByPublicId(publicId)
                }
            }
        }

        When("deleting an existing category") {

            When("the category has sub-categories") {
                coEvery { itemCategoryRepository.findByPublicId(publicId) } returns category
                coEvery { itemCategoryRepository.findByParentPublicId(publicId) } returns flowOf(subCategory)

                Then("it should throw CategoryDeleteFailException") {
                    shouldThrow<CategoryDeleteFailException> {
                        service.deleteItemCategoryByPublicId(publicId)
                    }
                }
            }

            When("the category has associated items") {
                coEvery { itemCategoryRepository.findByPublicId(publicId) } returns category
                coEvery { itemCategoryRepository.findByParentPublicId(publicId) } returns emptyFlow()
                coEvery { itemRepository.findByCategoryPublicId(publicId) } returns flowOf(item)

                Then("it should throw CategoryDeleteFailException") {
                    shouldThrow<CategoryDeleteFailException> {
                        service.deleteItemCategoryByPublicId(publicId)
                    }
                }
            }

            When("the category has no associated items or sub-categories") {
                coEvery { itemCategoryRepository.findByPublicId(publicId) } returns category
                coEvery { itemCategoryRepository.findByParentPublicId(publicId) } returns emptyFlow()
                coEvery { itemRepository.findByCategoryPublicId(publicId) } returns emptyFlow()
                coEvery { itemCategoryRepository.deleteByPublicId(publicId) } returns 1L

                Then("it should delete the category successfully") {
                    val result = service.deleteItemCategoryByPublicId(publicId)
                    result shouldBe 1L
                }
            }
        }
    }
})
