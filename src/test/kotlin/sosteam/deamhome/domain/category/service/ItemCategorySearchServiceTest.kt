package sosteam.deamhome.domain.category.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository

class ItemCategorySearchServiceTest : BehaviorSpec({

    val itemCategoryRepository = mockk<ItemCategoryRepository>()
    val itemCategorySearchService = ItemCategorySearchService(itemCategoryRepository)

    Given("a valid category sequence") {
        val categorySequence = 1L
        val mockItemCategory = ItemCategory(title = "Test Category", sequence = categorySequence)

        When("getting item category by sequence") {
            coEvery { itemCategoryRepository.findBySequence(categorySequence) } returns mockItemCategory

            Then("it should return the corresponding ItemCategoryResponse") {
                val result = itemCategorySearchService.getItemCategoryBySequence(categorySequence)
                result.title shouldBe mockItemCategory.title
            }
        }

        When("getting item category by non-existent sequence") {
            coEvery { itemCategoryRepository.findBySequence(categorySequence) } returns null

            Then("it should throw CategoryNotFoundException") {
                val exception = shouldThrow<CategoryNotFoundException> {
                    itemCategorySearchService.getItemCategoryBySequence(categorySequence)
                }
                exception.message shouldBe "존재하지 않는 카테고리입니다."
            }
        }
    }

    Given("a valid category title") {

        val mockItemCategory1 = ItemCategory(title = "Test Category 1", sequence = 1L)
        val mockItemCategory2 = ItemCategory(title = "Test Category 2", sequence = 2L)

        When("finding item categories containing the title") {
            coEvery { itemCategoryRepository.findItemCategoriesContainsTitle("Test") } returns flowOf(mockItemCategory1, mockItemCategory2)

            Then("it should return a Flow of corresponding ItemCategoryResponse") {
                val result = itemCategorySearchService.findItemCategoriesContainsTitle("Test")
                val toList = result.toList()
                toList.size shouldBe 2
                toList.map { it.title } shouldContain "Test Category 1"
                toList.map { it.title } shouldContain "Test Category 2"
            }
        }
    }

})
