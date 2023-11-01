package sosteam.deamhome.domain.item.service

import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.string.shouldContain
import io.mockk.coEvery

import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.entity.ItemDetailCategory
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.category.service.CategorySearchService

@OptIn(ExperimentalCoroutinesApi::class)
class CategorySearchServiceTest {

    @Test
    fun `findCategoryByTitle should return ItemCategoryDTO2`() = runTest{
        // Arrange
        val title = "Test Category"
        val itemCategoryRepository = mockk<ItemCategoryRepository>()
        val service = CategorySearchService(itemCategoryRepository)

        coEvery { itemCategoryRepository.findByTitle(title) } returns ItemCategory(title)
            .apply { modifyDetailCategory(mutableListOf(ItemDetailCategory("Detail1"), ItemDetailCategory("Detail2"))) }

        // Act
        val result = service.findCategoryByTitle(title)

        // Assert
        assertEquals(title, result.title)
        assertEquals(2, result.itemDetailCategories.size)
    }

    @Test
    fun `getItemsContainsTitle should return Flow of ItemCategoryDTO2`() = runTest {
        // Arrange
        val title1 = "Test Category"
        val title2 = "Test Category2"
        val searchKeyword = "Test"
        val itemCategoryRepository = mockk<ItemCategoryRepository>()
        val service = CategorySearchService(itemCategoryRepository)

        val itemCategory1 = ItemCategory(title1)
        val itemCategory2 = ItemCategory(title2)
        val itemDetailCategory1 = ItemDetailCategory("Detail1")
        val itemDetailCategory2 = ItemDetailCategory("Detail2")
        itemCategory1.modifyDetailCategory(mutableListOf(itemDetailCategory1))
        itemCategory2.modifyDetailCategory(mutableListOf(itemDetailCategory2))
        val itemCategoryList = listOf(itemCategory1, itemCategory2)
        val dtoList = itemCategoryList.map { it.toDTO() }

        coEvery { itemCategoryRepository.getItemsContainsTitle(searchKeyword) } returns dtoList.asFlow()

        // Act
        val result = service.getItemsContainsTitle(searchKeyword)

        // Assert
        result.collect {
            it.title shouldContain searchKeyword
            it.itemDetailCategories shouldHaveSize 1
        }
        val actual = result.toList()
        actual shouldContainAll dtoList
    }


}