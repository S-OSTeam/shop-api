package sosteam.deamhome.domain.category.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.category.dto.request.DetailCategoryRequestDTO
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.entity.ItemDetailCategory
import sosteam.deamhome.domain.category.exception.AlreadyExistDetailCategoryException
import sosteam.deamhome.domain.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.exception.CategorySaveFailException
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository

class DetailCategoryCreateServiceTest : FunSpec({

    val itemCategoryRepository = mockk<ItemCategoryRepository>()
    val detailCategoryCreateService = DetailCategoryCreateService(itemCategoryRepository)

    test("createDetailCategory should return ItemCategoryResponseDTO on successful detail category creation") {
        // Given
        val categoryTitle = "Test Category"
        val detailCategoryTitle = "Test Detail Category"
        val requestDTO = DetailCategoryRequestDTO(detailCategoryTitle, categoryTitle)
        val existingItemCategory = ItemCategory(title = categoryTitle)
        val newItemDetailCategory = ItemDetailCategory(title = detailCategoryTitle)

        coEvery { itemCategoryRepository.findByTitle(categoryTitle) } returns existingItemCategory
        coEvery { itemCategoryRepository.save(existingItemCategory) } returns Mono.just(existingItemCategory)

        // When
        val result = detailCategoryCreateService.createDetailCategory(requestDTO)

        // Then
        result.title shouldBe categoryTitle
        result.itemDetailCategories.map { it.title } shouldContain detailCategoryTitle
    }

    test("createDetailCategory should throw CategoryNotFoundException on non-existent category") {
        // Given
        val categoryTitle = "NonExistentCategory"
        val detailCategoryTitle = "Test Detail Category"
        val requestDTO = DetailCategoryRequestDTO(detailCategoryTitle, categoryTitle)

        coEvery { itemCategoryRepository.findByTitle(categoryTitle) } returns null

        // When, Then
        val exception = shouldThrow<CategoryNotFoundException> {
            detailCategoryCreateService.createDetailCategory(requestDTO)
        }

        exception.message shouldBe "존재하지 않는 카테고리입니다."
    }

    test("createDetailCategory should throw AlreadyExistDetailCategoryException on duplicate detail category") {
        // Given
        val categoryTitle = "Test Category"
        val detailCategoryTitle = "Existing Detail Category"
        val requestDTO = DetailCategoryRequestDTO(detailCategoryTitle, categoryTitle)
        val existingItemCategory = ItemCategory(title = categoryTitle).apply { itemDetailCategories = mutableListOf(ItemDetailCategory(title = detailCategoryTitle)) }

        coEvery { itemCategoryRepository.findByTitle(categoryTitle) } returns existingItemCategory

        // When, Then
        val exception = shouldThrow<AlreadyExistDetailCategoryException> {
            detailCategoryCreateService.createDetailCategory(requestDTO)
        }

        exception.message shouldBe "이미 존재하는 세부 카테고리입니다."
    }

    test("createDetailCategory should throw CategorySaveFailException on failed category save") {
        // Given
        val categoryTitle = "Test Category"
        val detailCategoryTitle = "Test Detail Category"
        val requestDTO = DetailCategoryRequestDTO(detailCategoryTitle, categoryTitle)
        val existingItemCategory = ItemCategory(title = categoryTitle)

        coEvery { itemCategoryRepository.findByTitle(categoryTitle) } returns existingItemCategory
        coEvery { itemCategoryRepository.save(existingItemCategory) } returns Mono.justOrEmpty(null)

        // When, Then
        val exception = shouldThrow<CategorySaveFailException> {
            detailCategoryCreateService.createDetailCategory(requestDTO)
        }

        exception.message shouldBe "카테고리 저장에 실패하였습니다."
    }
})