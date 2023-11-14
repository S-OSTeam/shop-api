package sosteam.deamhome.domain.category.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import sosteam.deamhome.domain.category.entity.ItemDetailCategory
import sosteam.deamhome.domain.category.exception.DetailCategoryNotFoundException
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository

class DetailCategorySearchServiceTest : FunSpec({

    val itemCategoryRepository = mockk<ItemCategoryRepository>()
    val detailCategorySearchService = DetailCategorySearchService(itemCategoryRepository)

    test("getItemDetailCategoryByTitle should return ItemDetailCategoryResponseDTO when detail category exists") {
        // Given
        val title = "TestDetailCategory"
        val expectedDetailCategory = ItemDetailCategory(title = title)
        coEvery { itemCategoryRepository.getItemDetailCategoryByTitle(title) } returns expectedDetailCategory

        // When
        val result = detailCategorySearchService.getItemDetailCategoryByTitle(title)

        // Then
        result.title shouldBe expectedDetailCategory.title
    }

    test("getItemDetailCategoryByTitle should throw DetailCategoryNotFoundException when detail category does not exist") {
        // Given
        val title = "NonExistentDetailCategory"
        coEvery { itemCategoryRepository.getItemDetailCategoryByTitle(title) } returns null

        // When, Then
        val exception = shouldThrow<DetailCategoryNotFoundException> {
            detailCategorySearchService.getItemDetailCategoryByTitle(title)
        }

        exception.message shouldBe "존재하지 않는 세부 카테고리입니다."
    }
})