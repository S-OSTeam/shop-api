package sosteam.deamhome.domain.item.service

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ItemServiceTest {
//    @MockK
//    private lateinit var itemRepository: ItemRepository
//    @MockK
//    private lateinit var accountRepository: AccountRepository
//    @MockK
//    private lateinit var itemCategoryRepository: ItemCategoryRepository
//    @MockK
//    private lateinit var itemDetailCategoryRepository: ItemDetailCategoryRepository
//
//    @InjectMockKs
//    private lateinit var itemService: ItemCreateService
//
//    @Test
//    fun createItemTest() = runBlocking {
//        // Given
//        val request = ItemCreateRequest(
//            title = "Test Item",
//            content = "Test Content",
//            summary = "Test Summary",
//            price = 100,
//            sellCnt = 0,
//            clickCnt = 0,
//            avgReview = 0.0,
//            reviewCnt = 0,
//            qnaCnt = 0,
//            status = false,
//            categoryTitle = "ct",
//            detailCategoryTitle = "dct",
//            imageId = mutableListOf("image1", "image2")
//        )
//
//        // Given
//        coEvery { accountRepository.findById(any<String>()) } returns Mono.just(mockk())
//        coEvery { itemCategoryRepository.findById(any<String>()) } returns Mono.just(mockk())
//        coEvery { itemDetailCategoryRepository.findById(any<String>()) } returns Mono.just(mockk())
//
//        coEvery { itemRepository.insert(any<Item>()) } returns Mono.just(mockk())
//
//        // When
//        val result: ItemDTO = itemService.createItem(request)
//
//        // Then
//        assertEquals("Test Item", result.title)
//        assertEquals(100, result.price)
//        coVerify(exactly = 1) { itemRepository.insert(any<Item>()) }
//    }

}