package sosteam.deamhome.domain.category.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.exception.MaxDepthExceedException
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository

class ItemCategoryUpdateServiceTest : BehaviorSpec({

    val itemCategoryRepository = mockk<ItemCategoryRepository>()
    val itemCategoryUpdateService = ItemCategoryUpdateService(itemCategoryRepository)

    Given("a source category and a destination category") {
        val sourceSeq = 1L
        val destinationSeq = 2L

        val sourceCategory = ItemCategory(
            title = "Source Category",
            sequence = sourceSeq,
            parentSeq = 3L,  // ParentSeq is set for testing purposes
            childrenSeq = mutableListOf()
        )

        val destinationCategory = ItemCategory(
            title = "Destination Category",
            sequence = destinationSeq,
            childrenSeq = mutableListOf()
        )

        val parentCategory = ItemCategory(
            title = "Parent Category",
            sequence = 3L,
            childrenSeq = mutableListOf(sourceSeq)
        )

        coEvery { itemCategoryRepository.findBySequence(sourceSeq) } returns sourceCategory
        coEvery { itemCategoryRepository.findBySequence(destinationSeq) } returns destinationCategory
        coEvery { itemCategoryRepository.findBySequence(3L) } returns parentCategory

        val mockItemCategory = ItemCategory(title = "Mock Category", sequence = 100L)
        coEvery { itemCategoryRepository.save(any()) } returns Mono.just(mockItemCategory)

        When("moving the source category to the destination category") {

            itemCategoryUpdateService.moveCategory(sourceSeq, destinationSeq)

            Then("the source category should have the correct parentSeq") {
                sourceCategory.parentSeq shouldBe destinationSeq
            }

            Then("the destination category should have the sourceSeq in its childrenSeq") {
                destinationCategory.childrenSeq shouldBe mutableListOf(sourceSeq)
            }

            Then("the parent category should not have the sourceSeq in its childrenSeq") {
                parentCategory.childrenSeq.shouldBeEmpty()
            }
        }
    }

    Given("a source category and a destination category with depth exceeding the maximum depth") {
        val testCategory1 = ItemCategory(title = "Test Category1", sequence = 1L, parentSeq = null)
        val testCategory2 = ItemCategory(title = "Test Category2", sequence = 2L, parentSeq = 1L)

        val sourceSeq = 3L
        val destinationSeq = 4L

        val sourceCategory = ItemCategory(
            title = "Source Category",
            sequence = sourceSeq,
            childrenSeq = mutableListOf()
        )

        val destinationCategory = ItemCategory(
            title = "Destination Category",
            sequence = destinationSeq,
            parentSeq = 2L,
            childrenSeq = mutableListOf()
        )

        coEvery { itemCategoryRepository.findBySequence(1L) } returns testCategory1
        coEvery { itemCategoryRepository.findBySequence(2L) } returns testCategory2
        coEvery { itemCategoryRepository.findBySequence(sourceSeq) } returns sourceCategory
        coEvery { itemCategoryRepository.findBySequence(destinationSeq) } returns destinationCategory

        val mockItemCategory = ItemCategory(title = "Mock Category", sequence = 100L)
        coEvery { itemCategoryRepository.save(any()) } returns Mono.just(mockItemCategory)

        When("moving the source category to the destination category with depth exceeding the maximum depth") {
            Then("it should throw MaxDepthExceedException") {
                val exception = shouldThrow<MaxDepthExceedException> {
                    itemCategoryUpdateService.moveCategory(sourceSeq, destinationSeq)
                }

                exception.message shouldBe "카테고리의 최대 깊이를 초과하였습니다."
            }
        }
    }

    Given("a source category and a non-existent destination category") {
        val sourceSeq = 7L
        val destinationSeq = 8L

        val sourceCategory = ItemCategory(
            title = "Source Category",
            sequence = sourceSeq,
            childrenSeq = mutableListOf()
        )

        coEvery { itemCategoryRepository.findBySequence(sourceSeq) } returns sourceCategory
        coEvery { itemCategoryRepository.findBySequence(destinationSeq) } returns null

        When("moving the source category to the non-existent destination category") {
            Then("it should throw CategoryNotFoundException for the destination category") {
                val exception = shouldThrow<CategoryNotFoundException> {
                    itemCategoryUpdateService.moveCategory(sourceSeq, destinationSeq)
                }

                exception.message shouldBe "이동할 카테고리를 찾을 수 없습니다."
            }
        }
    }

    Given("a non-existent source category and a destination category") {
        val sourceSeq = 10L
        val destinationSeq = 11L

        coEvery { itemCategoryRepository.findBySequence(sourceSeq) } returns null // Simulate non-existent source category

        When("moving the non-existent source category to the destination category") {
            Then("it should throw CategoryNotFoundException for the source category") {
                val exception = shouldThrow<CategoryNotFoundException> {
                    itemCategoryUpdateService.moveCategory(sourceSeq, destinationSeq)
                }

                exception.message shouldBe "존재하지 않는 카테고리입니다."
            }
        }
    }
})