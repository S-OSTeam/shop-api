package sosteam.deamhome.domain.item.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.exception.ItemNotFoundException
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.global.attribute.ItemStatus
import sosteam.deamhome.global.category.exception.CategoryNotFoundException
import java.time.OffsetDateTime

class ItemSearchServiceTest : BehaviorSpec({
	
	val itemRepository = mockk<ItemRepository>()
	val itemCategoryRepository = mockk<ItemCategoryRepository>()
	val service = ItemSearchService(itemRepository, itemCategoryRepository)
	
	Given("a valid categoryPublicId") {
		val category = ItemCategory(
			id = 1L,
			title = "Test Category",
			publicId = "testPublicId",
			parentPublicId = "testParentPublicId"
		)
		val categoryPublicId = category.publicId
		val item = Item(
			id = 1L,
			publicId = "testItemPublicId",
			categoryPublicId = categoryPublicId,
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
			sellerId = "",
			freeDelivery = false,
			stockCnt = 0,
			reviewScore = emptyList(),
			option = emptyList(),
			productNumber = "",
			deadline = OffsetDateTime.now(),
			originalWork = "",
			material = "",
			size = "",
			weight = "",
			shippingCost = 0
		)
		
		When("finding items by categoryPublicId") {
			coEvery { itemCategoryRepository.findByPublicId(categoryPublicId) } returns category
			coEvery { itemCategoryRepository.findByParentPublicId(category.publicId) } returns flowOf(category)
			coEvery { itemCategoryRepository.findByParentPublicIdIn(any()) } returns emptyFlow()
			coEvery { itemRepository.findByCategoryPublicIdIn(any()) } returns flowOf(item)
			
			Then("it should return a list of ItemResponse") {
				val items = service.findItemsByCategoryPublicId(categoryPublicId)
				items.first().title shouldBe item.title
			}
		}
		
		When("finding items by an invalid categoryPublicId") {
			coEvery { itemCategoryRepository.findByPublicId(categoryPublicId) } returns null
			
			Then("it should throw CategoryNotFoundException") {
				val exception = shouldThrow<CategoryNotFoundException> {
					service.findItemsByCategoryPublicId(categoryPublicId)
				}
				exception.extensions["code"] shouldBe "CATEGORY_NOT_FOUND"
			}
		}
	}
	
	Given("a valid item publicId") {
		val item = Item(
			id = 1L,
			publicId = "testItemPublicId",
			categoryPublicId = "testCategoryPublicId",
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
			sellerId = "",
			freeDelivery = false,
			stockCnt = 0,
			reviewScore = emptyList(),
			option = emptyList(),
			productNumber = "",
			deadline = OffsetDateTime.now(),
			originalWork = "",
			material = "",
			size = "",
			weight = "",
			shippingCost = 0
		)
		val publicId = item.publicId
		
		When("finding an item by publicId") {
			coEvery { itemRepository.findByPublicId(publicId) } returns item
			
			Then("it should return the corresponding ItemResponse") {
				val itemResponse = service.findItemByPublicId(publicId)
				itemResponse.title shouldBe item.title
			}
		}
		
		When("finding an item by an invalid publicId") {
			coEvery { itemRepository.findByPublicId(publicId) } returns null
			
			Then("it should throw ItemNotFoundException") {
				val exception = shouldThrow<ItemNotFoundException> {
					service.findItemByPublicId(publicId)
				}
				exception.extensions["code"] shouldBe "ITEM_NOT_FOUND"
			}
		}
	}
	
})
