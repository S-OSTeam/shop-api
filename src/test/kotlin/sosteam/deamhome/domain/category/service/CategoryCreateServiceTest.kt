package sosteam.deamhome.domain.category.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.category.dto.request.CategoryRequestDTO
import sosteam.deamhome.domain.category.exception.CategorySaveFailException


class CategoryCreateServiceTest : FunSpec({

    val itemCategoryRepository = mockk<ItemCategoryRepository>()
    val categoryCreateService = CategoryCreateService(itemCategoryRepository)

    test("createCategory should return ItemCategoryResponseDTO on successful category creation") {
        // Given
        val testTitle = "Test Category"
        val categoryRequestDTO = CategoryRequestDTO(title = testTitle)
        val newItemCategory = ItemCategory(title = categoryRequestDTO.title)

        coEvery { itemCategoryRepository.save(newItemCategory) } returns Mono.just(newItemCategory)

        // When
        val result = categoryCreateService.createCategory(categoryRequestDTO)

        // Then
        result.title shouldBe testTitle
    }

    test("createCategory should throw CategorySaveFailException on failed category creation") {
        // Given
        val categoryRequestDTO = CategoryRequestDTO(title = "Test Category")
        val newItemCategory = ItemCategory(title = categoryRequestDTO.title)

        coEvery { itemCategoryRepository.save(newItemCategory) } returns Mono.justOrEmpty(null)

        // When, Then
        val exception = shouldThrow<CategorySaveFailException> {
            categoryCreateService.createCategory(categoryRequestDTO)
        }

        exception.message shouldBe "카테고리 저장에 실패하였습니다."
    }
})