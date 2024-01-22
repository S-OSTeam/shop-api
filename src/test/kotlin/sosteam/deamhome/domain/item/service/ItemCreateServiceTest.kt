package sosteam.deamhome.domain.item.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.global.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.handler.request.ItemRequest
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.global.image.provider.ImageProvider
import sosteam.deamhome.global.sequence.provider.SequenceGenerator

class ItemCreateServiceTest : BehaviorSpec({

    val itemRepository = mockk<ItemRepository>()
    val itemCategoryRepository = mockk<ItemCategoryRepository>()
    val sequenceGenerator = mockk<SequenceGenerator>()
    val imageProvider = mockk<ImageProvider>()
    val itemCreateService = ItemCreateService(itemRepository, itemCategoryRepository, imageProvider, sequenceGenerator, "testSequence")
    val testSequence = 1L

    beforeTest {
        coEvery { sequenceGenerator.generateSequence(any()) } returns testSequence
    }

    Given("a valid item request") {
        val request = ItemRequest(
            title = "Test Item",
            categoryPublicId = 1L,
            sellerId = "seller123",
            content = "",
            summary = "",
            images = listOf()
        )

        val mockCategory = ItemCategory(title = "Test Category", publicId = 1L, parentPublicId = 1L)
        val mockSavedItem = Item(
            publicId = 1L,
            title = request.title,
            categoryPublicId = request.categoryPublicId,
            sellerId = request.sellerId,
            content = "",
            summary = ""
        ).apply { images = mutableListOf() }

        When("creating an item") {
            coEvery { itemCategoryRepository.findByPublicId(request.categoryPublicId) } returns mockCategory
            coEvery { imageProvider.saveImage(any(), any(), any()) } returns Mono.empty()
            coEvery { itemRepository.save(any()) } returns Mono.just(mockSavedItem)

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