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

    Given("a valid category publicId") {
        val categoryPublicId = 1L
        val mockDeletedCategory = ItemCategory(title = "Test Category", publicId = categoryPublicId, parentPublicId = 2L)

        When("deleting item category by publicId") {
            coEvery { itemCategoryRepository.deleteByPublicId(categoryPublicId) } returns mockDeletedCategory

            And("finding the parent category") {
                val mockParentCategory = ItemCategory(title = "Parent Category", publicId = 2L, childrenPublicId = mutableListOf(categoryPublicId))
                coEvery { itemCategoryRepository.findByPublicId(2L) } returns mockParentCategory

                And("saving the updated parent category") {
                    val mockDeletedParentCategory = ItemCategory(title = "Parent Category", publicId = 2L, childrenPublicId = mutableListOf())
                    coEvery { itemCategoryRepository.save(any()) } returns Mono.just(mockDeletedParentCategory)

                    Then("it should return the deleted category title") {
                        val result = itemCategoryDeleteService.deleteItemCategoryByPublicId(categoryPublicId)
                        result shouldBe mockDeletedCategory.title

                        coVerify(exactly = 1) { itemCategoryRepository.deleteByPublicId(categoryPublicId) }
                        coVerify(exactly = 1) { itemCategoryRepository.findByPublicId(2L) }

                        val parentCategorySlot = slot<ItemCategory>()
                        coVerify(exactly = 1) { itemCategoryRepository.save(capture(parentCategorySlot)) }

                        parentCategorySlot.captured.childrenPublicId shouldBe emptyList()
                    }
                }
            }
        }

        When("deleting non-existent item category by publicId") {
            coEvery { itemCategoryRepository.deleteByPublicId(categoryPublicId) } returns null
            val exception = shouldThrow<CategoryNotFoundException> {
                itemCategoryDeleteService.deleteItemCategoryByPublicId(categoryPublicId)
            }

            Then("it should throw CategoryNotFoundException") {
                exception.message shouldBe "존재하지 않는 카테고리입니다."
                exception.extensions["code"] shouldBe "CATEGORY_NOT_FOUND"
            }
        }

        When("deleting item category with a non-existent parent") {
            coEvery { itemCategoryRepository.deleteByPublicId(categoryPublicId) } returns mockDeletedCategory
            coEvery { itemCategoryRepository.findByPublicId(2L) } returns null
            val exception = shouldThrow<CategoryNotFoundException> {
                itemCategoryDeleteService.deleteItemCategoryByPublicId(categoryPublicId)
            }

            Then("it should throw CategoryNotFoundException") {
                exception.message shouldBe "상위 카테고리를 찾을 수 없습니다."
                exception.extensions["code"] shouldBe "CATEGORY_NOT_FOUND"
            }
        }
    }
})
