package sosteam.deamhome.domain.item.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.exception.ItemNotFoundException
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.domain.item.entity.ItemStatus
import sosteam.deamhome.global.image.provider.ImageProvider
import java.time.OffsetDateTime

class ItemDeleteServiceTest : BehaviorSpec({
	
	val itemRepository = mockk<ItemRepository>()
	val imageProvider = mockk<ImageProvider>()
	val service = ItemDeleteService(itemRepository, imageProvider)
	
	Given("a valid publicId") {
		val publicId = "testPublicId"
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
			storeId = "seller123",
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
			shippingCost = 0,
			imageUrls = emptyList()
		)
		
		When("deleting a non-existent item") {
			
			coEvery { itemRepository.findByPublicId(publicId) } returns null
			
			Then("it should throw ItemNotFoundException") {
				shouldThrow<ItemNotFoundException> {
					service.deleteItemByPublicId(publicId)
				}
			}
		}
		
		When("deleting an existing item") {
			val publicId = "existingPublicId"
			coEvery { itemRepository.findByPublicId(publicId) } returns item
			coEvery { imageProvider.deleteImage(any()) } returns true
			coEvery { itemRepository.deleteByPublicId(publicId) } returns 1L
			
			Then("it should delete the item and its associated images") {
				val result = service.deleteItemByPublicId(publicId)
				result shouldBe 1L
			}
		}
	}
})
