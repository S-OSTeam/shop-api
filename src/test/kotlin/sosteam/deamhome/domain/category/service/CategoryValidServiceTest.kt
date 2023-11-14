package sosteam.deamhome.domain.category.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.exception.AlreadyExistCategoryException
import sosteam.deamhome.domain.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository

class CategoryValidServiceTest : FunSpec({

    val itemCategoryRepository = mockk<ItemCategoryRepository>()
    val categoryValidService = CategoryValidService(itemCategoryRepository)

    test("getCategoryByTitle should return ItemCategory when category exists") {
        // Given
        val title = "TestCategory"
        val expectedCategory = ItemCategory(title = title)
        coEvery { itemCategoryRepository.findByTitle(title) } returns expectedCategory

        // When
        val result = categoryValidService.getCategoryByTitle(title)

        // Then
        result shouldBe expectedCategory
    }

    test("getCategoryByTitle should return null when category does not exist") {
        // Given
        val title = "NonExistentCategory"
        coEvery { itemCategoryRepository.findByTitle(title) } returns null

        // When
        val result = categoryValidService.getCategoryByTitle(title)

        // Then
        result shouldBe null
    }

    test("getItemCategoriesContainsTitle should return Flow of ItemCategoryResponseDTO") {
        // Given
        val title = "Test"
        val categories = listOf(
            ItemCategory(title = "TestCategory1"),
            ItemCategory(title = "TestCategory2")
        )
        coEvery { itemCategoryRepository.getItemCategoriesContainsTitle(title) } returns flow { categories.forEach { emit(it) } }


        // When
        val result = categoryValidService.getItemCategoriesContainsTitle(title)

        // Then
        val resultList = result.toList()
        val expectedTitles = categories.map { it.title }

        resultList.map { it.title } shouldContainExactly expectedTitles
    }

    test("isExistCategory should return ItemCategoryResponseDTO when category exists") {
        // Given
        val title = "TestExistCategory"
        val expectedCategory = ItemCategory(title = title)
        coEvery { itemCategoryRepository.findByTitle(title) } returns expectedCategory

        // When
        val result = categoryValidService.isExistCategory(title)

        // Then
        result.title shouldBe expectedCategory.title
    }

    test("isExistCategory should throw CategoryNotFoundException when category does not exist") {
        // Given
        val title = "NonExistentCategory"
        coEvery { itemCategoryRepository.findByTitle(title) } returns null

        // When, Then
        val exception = shouldThrow<CategoryNotFoundException> {
            categoryValidService.isExistCategory(title)
        }

        exception.message shouldBe "존재하지 않는 카테고리입니다."
    }

    test("isAlreadyExistCategory should throw AlreadyExistCategoryException when category exists") {
        // Given
        val title = "AlreadyExistCategory"
        coEvery { itemCategoryRepository.findByTitle(title) } returns ItemCategory(title = title)

        // When, Then
        val exception = shouldThrow<AlreadyExistCategoryException> {
            categoryValidService.isAlreadyExistCategory(title)
        }

        exception.message shouldBe "이미 존재하는 카테고리입니다."
    }

    test("isAlreadyExistCategory should return true when category does not exist") {
        // Given
        val title = "NonExistentCategory"
        coEvery { itemCategoryRepository.findByTitle(title) } returns null

        // When
        val result = categoryValidService.isAlreadyExistCategory(title)

        // Then
        result shouldBe true
    }

})