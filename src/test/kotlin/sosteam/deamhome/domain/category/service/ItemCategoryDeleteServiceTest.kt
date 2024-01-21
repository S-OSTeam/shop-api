//package sosteam.deamhome.domain.category.service
//
//import io.kotest.assertions.throwables.shouldThrow
//import io.kotest.core.spec.style.BehaviorSpec
//import io.kotest.matchers.shouldBe
//import io.mockk.coEvery
//import io.mockk.mockk
//import sosteam.deamhome.domain.category.entity.ItemCategory
//import sosteam.deamhome.domain.category.exception.CategoryNotFoundException
//import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
//
//class ItemCategoryDeleteServiceTest : BehaviorSpec({
//
//    val itemCategoryRepository = mockk<ItemCategoryRepository>()
//    val itemCategoryDeleteService = ItemCategoryDeleteService(itemCategoryRepository)
//
//    Given("a valid category publicId") {
//        val categoryPublicId = 1L
//        val mockDeletedCategory = ItemCategory(title = "Test Category", publicId = categoryPublicId, parentPublicId = 2L)
//
//        When("deleting item category by publicId") {
//            coEvery { itemCategoryRepository.deleteByPublicId(categoryPublicId) } returns mockDeletedCategory
//
//            Then("it should return the deleted category title") {
//                val result = itemCategoryDeleteService.deleteItemCategoryByPublicId(categoryPublicId)
//                result shouldBe mockDeletedCategory.title
//
//                coEvery { itemCategoryRepository.deleteByPublicId(categoryPublicId) }
//            }
//        }
//
//        When("deleting non-existent item category by publicId") {
//            coEvery { itemCategoryRepository.deleteByPublicId(categoryPublicId) } returns null
//            val exception = shouldThrow<CategoryNotFoundException> {
//                itemCategoryDeleteService.deleteItemCategoryByPublicId(categoryPublicId)
//            }
//
//            Then("it should throw CategoryNotFoundException") {
//                exception.extensions["code"] shouldBe "CATEGORY_NOT_FOUND"
//            }
//
//            coEvery { itemCategoryRepository.deleteByPublicId(categoryPublicId) }
//        }
//    }
//})
