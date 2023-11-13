package sosteam.deamhome.domain.item.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import sosteam.deamhome.domain.category.dto.response.ItemCategoryResponseDTO
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.category.service.CategoryValidService

@OptIn(ExperimentalCoroutinesApi::class)
class CategoryValidServiceTest : FunSpec({

    val categoryRepository = mockk<ItemCategoryRepository>()
    val categoryValidService = CategoryValidService(categoryRepository)

    test("getCategoryByTitle should return ItemCategory or null") {
        // Given
        val title = "test"
        val itemCategory = ItemCategory(
            title = title
        )
        coEvery { categoryRepository.findByTitle(title) } returns itemCategory

        // When
        val result = categoryValidService.getCategoryByTitle(title)

        // Then
        result shouldBe itemCategory
    }

    test("getCategoryByTitle should return null if category not found") {
        // Given
        val title = "nonexistent"
        coEvery { categoryRepository.findByTitle(title) } returns null

        // When
        val result = categoryValidService.getCategoryByTitle(title)

        // Then
        result shouldBe null
    }

    test("getItemCategoriesContainsTitle should return Flow of ItemCategoryResponseDTO") {
        // Given
        val title = "test"
        val itemCategory = ItemCategory(
            title = "TestCategory"
        )
        val itemCategoryResponseDTO = ItemCategoryResponseDTO.fromItemCategory(itemCategory)
        coEvery { categoryRepository.getItemCategoriesContainsTitle(title) } returns flowOf(itemCategoryResponseDTO)

        // When
        val result = categoryValidService.getItemCategoriesContainsTitle(title)

        // Then
        result.collect {
            it shouldBe itemCategoryResponseDTO
        }
        //TODO 안됨
    }

    test("isAlreadyExistCategory should return ItemCategoryResponseDTO if category exists") {
        // Given
        val title = "test"
        val itemCategory = ItemCategory(
            title = title
        )
        coEvery { categoryRepository.findByTitle(title) } returns itemCategory

        // When
        val result = categoryValidService.isAlreadyExistCategory(title)

        // Then
        result shouldBe ItemCategoryResponseDTO.fromItemCategory(itemCategory)
    }

    test("isAlreadyExistCategory should throw CategoryNotFoundException if category does not exist") {
        // Given
        val title = "nonexistent"
        coEvery { categoryRepository.findByTitle(title) } returns null

        // When, Then
        val exception = shouldThrow<CategoryNotFoundException> {
            categoryValidService.isAlreadyExistCategory(title)
        }

        exception.message shouldBe "존재하지 않는 카테고리입니다."
    }
})