package sosteam.deamhome.domain.category.service

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import sosteam.deamhome.domain.item.entity.ItemCategory
import sosteam.deamhome.domain.item.handler.request.QuestionCategoryRequest
import sosteam.deamhome.domain.item.service.ItemCategoryCreateService
import sosteam.deamhome.global.category.provider.CategoryProvider

class ItemCategoryCreateServiceTest : BehaviorSpec({
	
	val categoryProvider = mockk<CategoryProvider<ItemCategory>>()
	val itemCategoryCreateService = ItemCategoryCreateService(categoryProvider)
	
	Given("a valid category request") {
		val request = QuestionCategoryRequest(
			title = "Test Category",
			parentPublicId = null
		)
		
		val mockCategory = ItemCategory(
			id = 1L,
			title = request.title,
			publicId = "testPublicId",
			parentPublicId = "testPublicId"
		)
		
		When("creating a category") {
			coEvery { categoryProvider.saveCategory(any()) } returns mockCategory
			coEvery { categoryProvider.validateCategory(any()) } returns Unit
			
			val result = itemCategoryCreateService.createCategory(request)
			
			Then("it should return the corresponding ItemCategoryResponse") {
				result.title shouldBe "Test Category"
			}
		}
	}
	
})
