package sosteam.deamhome.domain.category.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.handler.response.ItemCategoryTreeResponse
import sosteam.deamhome.global.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.global.category.provider.CategoryProvider

class ItemCategorySearchServiceTest : BehaviorSpec({

    val itemCategoryRepository = mockk<ItemCategoryRepository>()
    val categoryProvider = mockk<CategoryProvider<ItemCategory>>()
    val service = ItemCategorySearchService(itemCategoryRepository, categoryProvider)

    Given("a valid publicId") {
        val publicId = "testPublicId"
        val itemCategory = ItemCategory(
            id = 1L,
            title = "Test Category",
            publicId = publicId,
            parentPublicId = "testParentPublicId"
        )

        When("finding an item category by publicId") {
            coEvery { itemCategoryRepository.findByPublicId(publicId) } returns itemCategory

            Then("it should return the corresponding ItemCategoryResponse") {
                val response = service.findItemCategoryByPublicId(publicId)
                response.title shouldBe itemCategory.title
            }
        }

        When("finding an item category with an invalid publicId") {
            coEvery { itemCategoryRepository.findByPublicId(publicId) } returns null

            Then("it should throw CategoryNotFoundException") {
                shouldThrow<CategoryNotFoundException> {
                    service.findItemCategoryByPublicId(publicId)
                }
            }
        }
    }

    Given("a title") {
        val title = "Test"
        val itemCategory1 = ItemCategory(
            id = 1L,
            title = "Test Category 1",
            publicId = "testPublicId1",
            parentPublicId = "testParentPublicId1"
        )
        val itemCategory2 = ItemCategory(
            id = 2L,
            title = "Test Category 2",
            publicId = "testPublicId2",
            parentPublicId = "testParentPublicId2"
        )

        When("finding item categories containing the title") {
            coEvery { itemCategoryRepository.findItemCategoriesContainTitle(title) } returns flowOf(itemCategory1, itemCategory2)

            Then("it should return a list of ItemCategoryResponse") {
                val responses = service.findItemCategoriesContainTitle(title).toList()
                responses.size shouldBe 2
                responses.map { it.title } shouldContainExactly listOf(itemCategory1.title, itemCategory2.title)
            }
        }
    }

    Given("all item categories") {
        val itemCategory1 = ItemCategory(
            id = 1L,
            title = "Test Category 1",
            publicId = "testPublicId1",
            parentPublicId = "testParentPublicId1"
        )
        val itemCategory2 = ItemCategory(
            id = 2L,
            title = "Test Category 2",
            publicId = "testPublicId2",
            parentPublicId = "testParentPublicId2"
        )
        val categoryTreeResponse1 = ItemCategoryTreeResponse(
            publicId = itemCategory1.publicId,
            title = itemCategory1.title,
            children = mutableListOf()
        )
        val categoryTreeResponse2 = ItemCategoryTreeResponse(
            publicId = itemCategory2.publicId,
            title = itemCategory2.title,
            children = mutableListOf()
        )

        When("finding all item categories in tree format") {
            coEvery { categoryProvider.findAllCategoriesTree(any()) } returns listOf(categoryTreeResponse1, categoryTreeResponse2)

            Then("it should return a list of ItemCategoryTreeResponse") {
                val responses = service.findAllItemCategoriesTree()
                responses.size shouldBe 2
                responses.map { it.title } shouldContainExactly listOf(itemCategory1.title, itemCategory2.title)
            }
        }
    }
})

