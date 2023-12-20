package sosteam.deamhome.domain.category.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository

class ItemCategoryDeleteServiceTest : BehaviorSpec({

    val itemCategoryRepository = mockk<ItemCategoryRepository>()
    val itemCategoryDeleteService = ItemCategoryDeleteService(itemCategoryRepository)

    Given("a valid category sequence") {
        val categorySequence = 1L
        val mockDeletedCategory = ItemCategory(title = "Test Category", sequence = categorySequence, parentSeq = 2L)

        When("deleting item category by sequence") {
            coEvery { itemCategoryRepository.deleteBySequence(categorySequence) } returns mockDeletedCategory

            And("finding the parent category") {
                val mockParentCategory = ItemCategory(title = "Parent Category", sequence = 2L, childrenSeq = mutableListOf(categorySequence))
                coEvery { itemCategoryRepository.findBySequence(2L) } returns mockParentCategory

                And("saving the updated parent category") {
                    val mockDeletedParentCategory = ItemCategory(title = "Parent Category", sequence = 2L, childrenSeq = mutableListOf())
                    coEvery { itemCategoryRepository.save(any()) } returns Mono.just(mockDeletedParentCategory)

                    Then("it should return the deleted category title") {
                        val result = itemCategoryDeleteService.deleteItemCategoryBySequence(categorySequence)
                        result shouldBe mockDeletedCategory.title

                        coVerify(exactly = 1) { itemCategoryRepository.deleteBySequence(categorySequence) }
                        coVerify(exactly = 1) { itemCategoryRepository.findBySequence(2L) }

                        val parentCategorySlot = slot<ItemCategory>()
                        coVerify(exactly = 1) { itemCategoryRepository.save(capture(parentCategorySlot)) }

                        parentCategorySlot.captured.childrenSeq shouldBe emptyList()
                    }
                }
            }
        }

        When("deleting non-existent item category by sequence") {
            coEvery { itemCategoryRepository.deleteBySequence(categorySequence) } returns null

            Then("it should throw CategoryNotFoundException") {
                val exception = shouldThrow<CategoryNotFoundException> {
                    itemCategoryDeleteService.deleteItemCategoryBySequence(categorySequence)
                }
                exception.message shouldBe "존재하지 않는 카테고리입니다."
            }
        }

        When("deleting item category with a non-existent parent") {
            coEvery { itemCategoryRepository.deleteBySequence(categorySequence) } returns mockDeletedCategory
            coEvery { itemCategoryRepository.findBySequence(2L) } returns null

            Then("it should throw CategoryNotFoundException") {
                val exception = shouldThrow<CategoryNotFoundException> {
                    itemCategoryDeleteService.deleteItemCategoryBySequence(categorySequence)
                }
                exception.message shouldBe "상위 카테고리를 찾을 수 없습니다."
            }
        }
    }
})
