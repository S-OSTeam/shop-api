package sosteam.deamhome.domain.category.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.category.handler.request.ItemCategoryRequest
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.exception.MaxDepthExceedException
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.global.sequence.provider.SequenceGenerator


class ItemCategoryCreateServiceTest : BehaviorSpec({

    val itemCategoryRepository = mockk<ItemCategoryRepository>()
    val sequenceGenerator = mockk<SequenceGenerator>()
    val itemCategoryCreateService = ItemCategoryCreateService(itemCategoryRepository, sequenceGenerator)
    val testSequence = 1L

    beforeTest {
        coEvery { sequenceGenerator.generateSequence(any()) } returns testSequence
    }

    Given("a valid CategoryRequest without a parent") {
        val categoryTitle = "Test Category"
        val validRequest = ItemCategoryRequest(title = categoryTitle, parentPublicId = null)

        val mockItemCategory = ItemCategory(
            title = categoryTitle,
            publicId = testSequence,
            parentPublicId = null,
        )

        coEvery { itemCategoryRepository.save(any()) } returns Mono.just(mockItemCategory)

        When("creating a category without a parent") {
            val result = itemCategoryCreateService.createCategory(validRequest)

            Then("it should return ItemCategoryResponse") {
                result.title shouldBe validRequest.title
                result.publicId shouldBe testSequence
            }
        }
    }

    Given("a valid CategoryRequest with a parent") {
        val categoryTitle = "Test Category"
        val childCategoryPublicId = 1L // sequenceGenerator 가 1 부터 만들어서
        val parentCategoryPublicId = 2L

        val validRequest = ItemCategoryRequest(title = categoryTitle, parentPublicId = parentCategoryPublicId)
        val parentCategory = ItemCategory(title = "Parent Category", publicId = parentCategoryPublicId)

        val mockItemCategory = ItemCategory(
            title = categoryTitle,
            publicId = childCategoryPublicId,
            parentPublicId = parentCategoryPublicId,
        )

        coEvery { itemCategoryRepository.save(any()) } returns Mono.just(mockItemCategory)

        coEvery { itemCategoryRepository.findByPublicId(parentCategoryPublicId) } returns parentCategory

        When("creating a category with a parent") {
            val result = itemCategoryCreateService.createCategory(validRequest)

            Then("it should return ItemCategoryResponse") {
                result.title shouldBe validRequest.title
                result.publicId shouldBe childCategoryPublicId
                result.parentPublicId shouldBe parentCategoryPublicId
            }
        }
    }

    Given("an invalid CategoryRequest") {
        val categoryTitle = "Test Category"
        val testCategory1 = ItemCategory(title = "Test Category1", publicId = 1L, parentPublicId = null)
        val testCategory2 = ItemCategory(title = "Test Category2", publicId = 2L, parentPublicId = 1L)
        val testCategory3 = ItemCategory(title = "Test Category3", publicId = 3L, parentPublicId = 2L)
        val invalidRequest = ItemCategoryRequest(title = categoryTitle, parentPublicId = 3L)

        coEvery { itemCategoryRepository.findByPublicId(1L) } returns testCategory1
        coEvery { itemCategoryRepository.findByPublicId(2L) } returns testCategory2
        coEvery { itemCategoryRepository.findByPublicId(3L) } returns testCategory3

        val mockItemCategory = ItemCategory(title = categoryTitle, publicId = testSequence)
        coEvery { itemCategoryRepository.save(any()) } returns Mono.just(mockItemCategory)

        When("creating a category with a parent exceeding the maximum depth") {
            val exception = shouldThrow<MaxDepthExceedException> {
                itemCategoryCreateService.createCategory(invalidRequest)
            }

            Then("it should throw MaxDepthExceedException") {
                exception.message shouldBe "카테고리의 최대 깊이를 초과하였습니다."
                exception.extensions["code"] shouldBe "MAX_DEPTH_EXCEED"
            }
        }
    }

    Given("a CategoryRequest with a non-existent parent") {
        val invalidParentRequest = ItemCategoryRequest(title = "Test Category", parentPublicId = 123L)

        coEvery { itemCategoryRepository.findByPublicId(123L) } returns null

        When("creating a category with a non-existent parent") {
            val exception = shouldThrow<CategoryNotFoundException> {
                itemCategoryCreateService.createCategory(invalidParentRequest)
            }

            Then("it should throw CategoryNotFoundException") {
                exception.message shouldBe "상위 카테고리를 찾을 수 없습니다."
                exception.extensions["code"] shouldBe "CATEGORY_NOT_FOUND"
            }
        }
    }

})
