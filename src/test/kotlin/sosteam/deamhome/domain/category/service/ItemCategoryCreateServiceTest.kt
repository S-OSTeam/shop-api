package sosteam.deamhome.domain.category.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.category.dto.request.ItemCategoryRequest
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.exception.CategorySaveFailException
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

    Given("a valid CategoryRequest") {
        val categoryTitle = "Test Category"
        val validRequest = ItemCategoryRequest(title = categoryTitle, parentSeq = null)

        When("creating a category without a parent") {
            val mockItemCategory = ItemCategory(
                title = categoryTitle,
                sequence = testSequence,
                parentSeq = null,
                childrenSeq = mutableListOf()
            )
            coEvery { itemCategoryRepository.save(any()) } returns Mono.just(mockItemCategory)

            Then("it should return ItemCategoryResponse") {
                val result = itemCategoryCreateService.createCategory(validRequest)
                result.title shouldBe validRequest.title
                result.sequence shouldBe testSequence
            }
        }

        When("creating a category with a parent") {
            val parentCategorySeq = 1L
            val parentCategory = ItemCategory(
                title = "Parent Category",
                sequence = parentCategorySeq,
                childrenSeq = mutableListOf()
            )
            val mockItemCategory = ItemCategory(
                title = categoryTitle,
                sequence = testSequence + 1,
                parentSeq = parentCategorySeq,
                childrenSeq = mutableListOf()
            )

            coEvery { itemCategoryRepository.findBySequence(parentCategorySeq) } returns parentCategory
            coEvery { itemCategoryRepository.save(any()) } returns Mono.just(mockItemCategory)

            Then("it should return ItemCategoryResponse") {
                val result = itemCategoryCreateService.createCategory(validRequest.copy(parentSeq = parentCategorySeq))
                result.title shouldBe validRequest.title
                result.sequence shouldBe testSequence + 1

                //TODO Verify that the parentCategory is updated
                verify { itemCategoryRepository.save(parentCategory) }
            }
        }
    }

    Given("an invalid CategoryRequest") {
        val categoryTitle = "Test Category"
        val testCategory1 = ItemCategory(title = "Test Category1", sequence = 1L, parentSeq = null)
        val testCategory2 = ItemCategory(title = "Test Category1", sequence = 2L, parentSeq = 1L)
        val testCategory3 = ItemCategory(title = "Test Category1", sequence = 3L, parentSeq = 2L)
        val invalidRequest = ItemCategoryRequest(title = categoryTitle, parentSeq = 3L)

        When("creating a category with a parent exceeding the maximum depth") {
            val mockItemCategory = ItemCategory(
                title = categoryTitle,
                sequence = testSequence,
                parentSeq = null,
                childrenSeq = mutableListOf()
            )

            coEvery { itemCategoryRepository.findBySequence(1L) } returns testCategory1
            coEvery { itemCategoryRepository.findBySequence(2L) } returns testCategory2
            coEvery { itemCategoryRepository.findBySequence(3L) } returns testCategory3
            coEvery { itemCategoryRepository.save(any()) } returns Mono.just(mockItemCategory)

            Then("it should throw MaxDepthExceedException") {
                val exception = shouldThrow<MaxDepthExceedException> {
                    itemCategoryCreateService.createCategory(invalidRequest)
                }

                exception.message shouldBe "카테고리의 최대 깊이를 초과하였습니다."
            }
        }
    }

    Given("a CategoryRequest with a non-existent parent") {
        val invalidParentRequest = ItemCategoryRequest(title = "Test Category", parentSeq = 123L)

        When("creating a category with a non-existent parent") {
            coEvery { itemCategoryRepository.findBySequence(123L) } returns null

            Then("it should throw CategoryNotFoundException") {
                val exception = shouldThrow<CategoryNotFoundException> {
                    itemCategoryCreateService.createCategory(invalidParentRequest)
                }

                exception.message shouldBe "상위 카테고리를 찾을 수 없습니다."
            }
        }
    }

    Given("a CategoryRequest with a parent, but fails to save the category") {
        val parentCategorySeq = 1L
        val saveFailRequest = ItemCategoryRequest(title = "Test Category", parentSeq = parentCategorySeq)
        val parentCategory = ItemCategory(
            title = "Parent Category",
            sequence = parentCategorySeq,
            childrenSeq = mutableListOf()
        )

        When("creating a category with a parent") {
            coEvery { itemCategoryRepository.findBySequence(1L) } returns parentCategory
            coEvery { itemCategoryRepository.save(any()) } returns Mono.empty()

            Then("it should throw CategorySaveFailException") {
                val exception = shouldThrow<CategorySaveFailException> {
                    itemCategoryCreateService.createCategory(saveFailRequest)
                }

                exception.message shouldBe "카테고리 저장에 실패하였습니다."
            }
        }
    }
})
