package sosteam.deamhome.domain.item.service

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import sosteam.deamhome.domain.item.entity.dto.ItemDTO
import sosteam.deamhome.domain.item.repository.ItemRepository


class ItemSearchServiceTest : FunSpec({

    val itemRepository = mockk<ItemRepository>()
    val itemSearchService = ItemSearchService(itemRepository)

    test("getItemsContainsTitle should return flow of ItemDTO") {
        // Given
        val title = "test"
        val itemDTO = ItemDTO(
            title = "testTitle",
            content = "testContent",
            summary = "testSummary",
            price = 100,
            sellCnt = 1000,
            wishCnt = 10000,
            clickCnt = 100000,
            avgReview = 5.0,
            reviewCnt = 500,
            qnaCnt = 50,
            status = true,
            images = listOf("imgeURL1", "imageURL2"),
            account = null
        )
        coEvery { itemRepository.getItemsContainsTitle(title) } returns flowOf(itemDTO, itemDTO)

        // When
        val result = itemSearchService.getItemsContainsTitle(title)

        // Then
        result.collect{
          it shouldBe itemDTO
        }
    }
})