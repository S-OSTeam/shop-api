package sosteam.deamhome.domain.item.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.exception.ItemNotFoundException
import sosteam.deamhome.domain.item.handler.request.ItemUpdateRequest
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.global.attribute.ItemStatus
import sosteam.deamhome.global.image.provider.ImageProvider
import java.time.OffsetDateTime

class ItemUpdateServiceTest : BehaviorSpec({
	
	val itemRepository = mockk<ItemRepository>()
	val itemCategoryRepository = mockk<ItemCategoryRepository>()
	val imageProvider = mockk<ImageProvider>()
	val service = ItemUpdateService(itemRepository, itemCategoryRepository, imageProvider)
	
	Given("a valid update request") {
		val request = ItemUpdateRequest(
			publicId = "testPublicId",
			categoryPublicId = "testCategoryPublicId",
			title = "Updated Title",
			content = "Updated Content",
			summary = "Updated Summary",
			price = 20000,
			status = ItemStatus.AVAILABLE,
			storeId = "updated_seller123",
			freeDelivery = true,
			option = listOf("updated_option1", "updated_option2"),
			productNumber = "updated_12345",
			deadline = null,
			originalWork = null,
			material = null,
			size = null,
			weight = null,
			shippingCost = null,
			imageUrls = null
		)
		
		When("updating an existing item") {
			val existingItem = Item(
				id = 1L,
				publicId = request.publicId,
				categoryPublicId = "original_categoryPublicId",
				title = "Original Title",
				content = "Original Content",
				summary = "Original Summary",
				price = 10000,
				status = ItemStatus.SOLDOUT,
				storeId = "original_seller123",
				freeDelivery = false,
				option = listOf("original_option1", "original_option2"),
				productNumber = "original_12345",
				deadline = OffsetDateTime.now(),
				originalWork = "",
				material = "",
				size = "",
				weight = "",
				shippingCost = 10000
			)
			
			val updatedItem = existingItem.copy(
				categoryPublicId = request.categoryPublicId!!,
				title = request.title!!,
				content = request.content!!,
				summary = request.summary!!,
				price = request.price!!,
				status = request.status!!,
				storeId = request.storeId!!,
				freeDelivery = request.freeDelivery!!,
				option = request.option!!,
				productNumber = request.productNumber!!
			)
			
			val updatedCategory = ItemCategory(
				id = 1L,
				title = "Test Category",
				publicId = request.categoryPublicId!!,
				parentPublicId = "testParentPublicId"
			)
			
			coEvery { itemRepository.findByPublicId(request.publicId) } returns existingItem
			coEvery { itemCategoryRepository.findByPublicId(request.categoryPublicId!!) } returns updatedCategory
			coEvery { itemRepository.save(updatedItem) } returns updatedItem
			
			Then("it should return the updated item") {
				val response = service.updateItem(request)
				response.categoryPublicId shouldBe request.categoryPublicId
				response.title shouldBe request.title
				response.summary shouldBe request.summary
				response.price shouldBe request.price
				response.status shouldBe request.status
				response.storeId shouldBe request.storeId
				response.freeDelivery shouldBe request.freeDelivery
				response.option shouldBe request.option
				response.productNumber shouldBe request.productNumber
			}
		}
		
		When("updating an item with invalid publicId") {
			coEvery { itemRepository.findByPublicId(request.publicId) } returns null
			
			Then("it should throw ItemNotFoundException") {
				shouldThrow<ItemNotFoundException> {
					service.updateItem(request)
				}
			}
		}
	}
})