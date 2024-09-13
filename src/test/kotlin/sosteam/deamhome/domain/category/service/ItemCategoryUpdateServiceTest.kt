package sosteam.deamhome.domain.category.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow
import sosteam.deamhome.domain.item.entity.ItemCategory
import sosteam.deamhome.domain.item.handler.request.ItemCategoryUpdateRequest
import sosteam.deamhome.domain.item.repository.ItemCategoryRepository
import sosteam.deamhome.domain.item.service.ItemCategoryUpdateService
import sosteam.deamhome.global.category.exception.CategoryNotFoundException
import sosteam.deamhome.global.category.exception.MaxDepthExceedException
import sosteam.deamhome.global.category.provider.CategoryProvider

class ItemCategoryUpdateServiceTest : BehaviorSpec({
	
	val itemCategoryRepository = mockk<ItemCategoryRepository>()
	val categoryProvider = mockk<CategoryProvider<ItemCategory>>()
	val service = ItemCategoryUpdateService(itemCategoryRepository, categoryProvider)
	
	Given("a valid update request") {
		val request = ItemCategoryUpdateRequest(
			publicId = "testPublicId",
			title = "Updated Title",
			parentPublicId = "newParentPublicId"
		)
		
		When("updating an existing item category") {
			val existingCategory = ItemCategory(
				id = 1L,
				title = "Original Title",
				publicId = request.publicId,
				parentPublicId = "originalParentPublicId"
			)
			val updatedCategory = existingCategory.copy(
				title = request.title!!,
				parentPublicId = request.parentPublicId!!
			)
			
			coEvery { itemCategoryRepository.findByPublicId(request.publicId) } returns existingCategory
			coEvery {
				categoryProvider.validateParentCategory(
					request.parentPublicId!!,
					request.title!!,
					existingCategory.maxDepth
				)
			} returns 1
			coEvery { itemCategoryRepository.save(updatedCategory) } returns updatedCategory
			coEvery { itemCategoryRepository.findByParentPublicId(request.publicId) } returns emptyFlow()
			
			Then("it should return the updated item category") {
				val response = service.updateItemCategory(request)
				response.title shouldBe updatedCategory.title
				response.publicId shouldBe updatedCategory.publicId
				response.parentPublicId shouldBe updatedCategory.parentPublicId
			}
		}
		
		When("updating an item category with invalid publicId") {
			coEvery { itemCategoryRepository.findByPublicId(request.publicId) } returns null
			
			Then("it should throw CategoryNotFoundException") {
				shouldThrow<CategoryNotFoundException> {
					service.updateItemCategory(request)
				}
			}
		}
		
		When("updating an item category with parentPublicId that exceeds the max depth") {
			val existingCategory = ItemCategory(
				id = 1L,
				title = "Original Title",
				publicId = request.publicId,
				parentPublicId = "originalParentPublicId"
			)
			
			coEvery { itemCategoryRepository.findByPublicId(request.publicId) } returns existingCategory
			coEvery {
				categoryProvider.validateParentCategory(
					request.parentPublicId!!,
					request.title!!,
					existingCategory.maxDepth
				)
			} throws MaxDepthExceedException()
			
			Then("it should throw MaxDepthExceedException") {
				shouldThrow<MaxDepthExceedException> {
					service.updateItemCategory(request)
				}
			}
		}
	}
})
