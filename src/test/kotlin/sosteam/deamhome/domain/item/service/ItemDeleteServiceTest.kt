package sosteam.deamhome.domain.item.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.exception.ItemNotFoundException
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.global.image.provider.ImageProvider

class ItemDeleteServiceTest : BehaviorSpec({

    val itemRepository = mockk<ItemRepository>()
    val imageProvider = mockk<ImageProvider>()
    val itemDeleteService = ItemDeleteService(itemRepository, imageProvider)

    Given("an existing item's publicId") {
        val publicId = 1L
        val existingItemTitle = "Existing Item"
        val mockItem = Item(title = existingItemTitle, publicId = publicId, content = "", sellerId = "", summary = "")

        When("deleting the item") {
            coEvery { itemRepository.findByPublicId(publicId) } returns mockItem
            coEvery { itemRepository.deleteByPublicId(publicId) } returns mockItem
            val result = itemDeleteService.deleteItemByPublicId(publicId)

            Then("it should return the title of the deleted item") {
                result shouldBe existingItemTitle
            }
        }
    }

    Given("a non-existent item's publicId") {
        val nonExistentPublicId = 2L

        When("deleting the item") {
            coEvery { itemRepository.findByPublicId(nonExistentPublicId) } returns null
            val exception = shouldThrow<ItemNotFoundException> {
                itemDeleteService.deleteItemByPublicId(nonExistentPublicId)
            }

            Then("it should throw ItemNotFoundException") {
                exception.message shouldBe "존재하지 않는 아이템입니다."
                exception.extensions["code"] shouldBe "ITEM_NOT_FOUND"
            }
        }
    }
})