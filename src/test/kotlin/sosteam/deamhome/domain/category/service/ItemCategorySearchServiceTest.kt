package sosteam.deamhome.domain.category.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.global.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository

class ItemCategorySearchServiceTest : BehaviorSpec({

    val itemCategoryRepository = mockk<ItemCategoryRepository>()
    val itemCategorySearchService = ItemCategorySearchService(itemCategoryRepository)

    Given("a valid category publicId") {
        val categoryPublicId = 1L
        val mockItemCategory = ItemCategory(title = "Test Category", publicId = categoryPublicId, parentPublicId = categoryPublicId)

        When("finding item category by publicId") {
            coEvery { itemCategoryRepository.findByPublicId(categoryPublicId) } returns mockItemCategory
            val result = itemCategorySearchService.findItemCategoryByPublicId(categoryPublicId)

            Then("it should return the corresponding ItemCategoryResponse") {
                result.title shouldBe mockItemCategory.title
            }
        }

        When("finding item category by non-existent publicId") {
            coEvery { itemCategoryRepository.findByPublicId(categoryPublicId) } returns null
            val exception = shouldThrow<CategoryNotFoundException> {
                itemCategorySearchService.findItemCategoryByPublicId(categoryPublicId)
            }

            Then("it should throw CategoryNotFoundException") {
                exception.extensions["code"] shouldBe "CATEGORY_NOT_FOUND"
            }
        }
    }



    Given("a valid category title") {
        val mockItemCategory1 = ItemCategory(title = "Test Category 1", publicId = 1L, parentPublicId = 1L)
        val mockItemCategory2 = ItemCategory(title = "Test Category 2", publicId = 2L, parentPublicId = 2L)

        When("finding item categories containing the title") {
            coEvery { itemCategoryRepository.findItemCategoriesContainTitle("Test") } returns flowOf(mockItemCategory1, mockItemCategory2)
            val result = itemCategorySearchService.findItemCategoriesContainTitle("Test")

            Then("it should return a Flow of corresponding ItemCategoryResponse") {
                val toList = result.toList()
                toList.size shouldBe 2
                toList.map { it.title } shouldContain "Test Category 1"
                toList.map { it.title } shouldContain "Test Category 2"
            }
        }

        When("finding item category by title") {
            coEvery { itemCategoryRepository.findByTitle("Test Category 1") } returns mockItemCategory1
            val result = itemCategorySearchService.findItemCategoryByTitle("Test Category 1")

            Then("it should return the corresponding ItemCategoryResponse") {
                result.title shouldBe mockItemCategory1.title
            }
        }

        When("finding item category by non-existent title") {
            coEvery { itemCategoryRepository.findByTitle("Test Category 3") } returns null
            val exception = shouldThrow<CategoryNotFoundException> {
                itemCategorySearchService.findItemCategoryByTitle("Test Category 3")
            }

            Then("it should throw CategoryNotFoundException") {
                exception.extensions["code"] shouldBe "CATEGORY_NOT_FOUND"
            }
        }

    }

    Given("a list of item categories") {
        val category1 = ItemCategory(title = "Category 1", publicId = 1L, parentPublicId = 1L)
        val category2 = ItemCategory(title = "Category 2", publicId = 2L, parentPublicId = 1L)
        val category3 = ItemCategory(title = "Category 3", publicId = 3L, parentPublicId = 1L)

        coEvery { itemCategoryRepository.findAllItemCategories() } returns flowOf(category1, category2, category3)

        When("finding all item categories as a tree") {
            val result = itemCategorySearchService.findAllItemCategoriesTree()

            Then("it should return a list of ItemCategoryTreeResponse with correct hierarchy") {
                result.size shouldBe 1
                result[0].title shouldBe "Category 1"
                result[0].children.size shouldBe 2
                result[0].children.map { it.title } shouldContainExactly listOf("Category 2", "Category 3")
            }
        }

    }

})
