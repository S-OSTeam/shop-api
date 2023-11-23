package sosteam.deamhome.domain.category.service

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository

class CategoryDeleteServiceTest : FunSpec({

    val itemCategoryRepository = mockk<ItemCategoryRepository>()
    val categoryDeleteService = CategoryDeleteService(itemCategoryRepository)

    test("deleteItemCategoryById should call repository's deleteItemCategoryById method with the correct id") {
        // Given
        val categoryId = "testCategoryId"
        coEvery { itemCategoryRepository.deleteItemCategoryById(categoryId) } returns 1L

        // When
        val result = categoryDeleteService.deleteItemCategoryById(categoryId)

        // Then
        coVerify(exactly = 1) {
            itemCategoryRepository.deleteItemCategoryById(categoryId)
        }
        result shouldBe 1L
    }

})