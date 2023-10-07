package sosteam.deamhome.domain.item.service

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.entity.dto.ItemDTO
import sosteam.deamhome.domain.item.repository.ItemCategoryRepository
import sosteam.deamhome.domain.item.repository.ItemDetailCategoryRepository
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.domain.item.resolver.request.ItemCreateRequest

@ExtendWith(MockKExtension::class)
class ItemServiceTest {
    @MockK
    private lateinit var itemRepository: ItemRepository
    @MockK
    private lateinit var accountRepository: AccountRepository
    @MockK
    private lateinit var itemCategoryRepository: ItemCategoryRepository
    @MockK
    private lateinit var itemDetailCategoryRepository: ItemDetailCategoryRepository

    @InjectMockKs
    private lateinit var itemService: ItemService

    @Test
    fun createItemTest() = runBlocking {
        // Given
        val request = ItemCreateRequest(
            title = "Test Item",
            content = "Test Content",
            summary = "Test Summary",
            price = 100,
            accountId = "test_account_id",
            itemCategoryId = "test_category_id",
            itemDetailCategoryId = "test_detail_category_id",
            imageUrls = listOf("image_url_1", "image_url_2")
        )

        // Given
        coEvery { accountRepository.findById(any<String>()) } returns Mono.just(mockk())
        coEvery { itemCategoryRepository.findById(any<String>()) } returns Mono.just(mockk())
        coEvery { itemDetailCategoryRepository.findById(any<String>()) } returns Mono.just(mockk())

        coEvery { itemRepository.insert(any<Item>()) } returns Mono.just(mockk())

        // When
        val result: ItemDTO = itemService.createItem(request)

        // Then
        assertEquals("Test Item", result.title)
        assertEquals(100, result.price)
        coVerify(exactly = 1) { itemRepository.insert(any<Item>()) }
    }

}