package sosteam.deamhome.domain.category.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.*
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

    Given("a valid CategoryRequest without a parent") {
        val categoryTitle = "Test Category"
        val validRequest = ItemCategoryRequest(title = categoryTitle, parentSeq = null)

        val mockItemCategory = ItemCategory(
            title = categoryTitle,
            sequence = testSequence,
            parentSeq = null,
            childrenSeq = mutableListOf()
        )

        coEvery { itemCategoryRepository.save(any()) } returns Mono.just(mockItemCategory)

        When("creating a category without a parent") {
            val result = itemCategoryCreateService.createCategory(validRequest)

            Then("it should return ItemCategoryResponse") {
                result.title shouldBe validRequest.title
                result.sequence shouldBe testSequence
            }
        }
    }

    Given("a valid CategoryRequest with a parent") {
        val categoryTitle = "Test Category"
        val childCategorySeq = 1L // sequenceGenerator 가 1 부터 만들어서
        val parentCategorySeq = 2L

        val validRequest = ItemCategoryRequest(title = categoryTitle, parentSeq = parentCategorySeq)
        val parentCategory = ItemCategory(title = "Parent Category", sequence = parentCategorySeq)

        val mockItemCategory = ItemCategory(
            title = categoryTitle,
            sequence = childCategorySeq,
            parentSeq = parentCategorySeq,
            childrenSeq = mutableListOf()
        )

        coEvery { itemCategoryRepository.findBySequence(parentCategorySeq) } returns parentCategory
        val parentCategorySlot = mutableListOf<ItemCategory>()
        coEvery { itemCategoryRepository.save(capture(parentCategorySlot)) } returns Mono.just(mockItemCategory)

        When("creating a category with a parent") {
            val result = itemCategoryCreateService.createCategory(validRequest)

            Then("it should return ItemCategoryResponse") {
                result.title shouldBe validRequest.title
                result.sequence shouldBe childCategorySeq
                result.parentSeq shouldBe parentCategorySeq

                println(parentCategorySlot)

                val capturedParentCategory = parentCategorySlot.find { it.title == "Parent Category" }
                capturedParentCategory shouldNotBe null
                capturedParentCategory!!.childrenSeq shouldContain childCategorySeq

            }
        }
    }

    Given("an invalid CategoryRequest") {
        val categoryTitle = "Test Category"
        val testCategory1 = ItemCategory(title = "Test Category1", sequence = 1L, parentSeq = null)
        val testCategory2 = ItemCategory(title = "Test Category2", sequence = 2L, parentSeq = 1L)
        val testCategory3 = ItemCategory(title = "Test Category3", sequence = 3L, parentSeq = 2L)
        val invalidRequest = ItemCategoryRequest(title = categoryTitle, parentSeq = 3L)

        coEvery { itemCategoryRepository.findBySequence(1L) } returns testCategory1
        coEvery { itemCategoryRepository.findBySequence(2L) } returns testCategory2
        coEvery { itemCategoryRepository.findBySequence(3L) } returns testCategory3

        val mockItemCategory = ItemCategory(title = categoryTitle, sequence = testSequence)
        coEvery { itemCategoryRepository.save(any()) } returns Mono.just(mockItemCategory)

        When("creating a category with a parent exceeding the maximum depth") {
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

        coEvery { itemCategoryRepository.findBySequence(123L) } returns null

        When("creating a category with a non-existent parent") {
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

        coEvery { itemCategoryRepository.findBySequence(1L) } returns parentCategory
        coEvery { itemCategoryRepository.save(any()) } returns Mono.empty()

        When("creating a category with a parent") {
            Then("it should throw CategorySaveFailException") {
                val exception = shouldThrow<CategorySaveFailException> {
                    itemCategoryCreateService.createCategory(saveFailRequest)
                }

                exception.message shouldBe "카테고리 저장에 실패하였습니다."
            }
        }
    }
})
