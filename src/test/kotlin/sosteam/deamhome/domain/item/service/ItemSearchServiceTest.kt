//package sosteam.deamhome.domain.item.service
//
//import io.kotest.core.spec.style.BehaviorSpec
//import io.kotest.matchers.collections.shouldContain
//import io.kotest.matchers.shouldBe
//import io.mockk.coEvery
//import io.mockk.mockk
//import kotlinx.coroutines.flow.emptyFlow
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.flow.toList
//import sosteam.deamhome.domain.category.entity.ItemCategory
//import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
//import sosteam.deamhome.domain.item.entity.Item
//import sosteam.deamhome.domain.item.repository.ItemRepository
//
//class ItemSearchServiceTest : BehaviorSpec({
//
//    val itemRepository = mockk<ItemRepository>()
//    val itemCategoryRepository = mockk<ItemCategoryRepository>()
//    val itemSearchService = ItemSearchService(itemRepository, itemCategoryRepository)
//
//    Given("a valid item title") {
//        val title1 = "Test Item 1"
//        val title2 = "Test Item 2"
//        val mockItem1 = Item(title = title1, publicId = 1L, content = "", sellerId = "", summary = "")
//        val mockItem2 = Item(title = title2, publicId = 2L, content = "", sellerId = "", summary = "")
//
//        When("finding items containing the title") {
//            coEvery { itemRepository.findItemsContainTitle("Test") } returns flowOf(mockItem1, mockItem2)
//            val result = itemSearchService.findItemsContainTitle("Test")
//            val toList = result.toList()
//
//            Then("it should return a Flow of corresponding ItemResponse") {
//                toList.size shouldBe 2
//                toList.map { it.title } shouldContain "Test Item 1"
//                toList.map { it.title } shouldContain "Test Item 2"
//            }
//        }
//    }
//
//    Given("a valid item publicId") {
//        val itemPublicId = 1L
//        val mockItem = Item(title = "Test Item", publicId = itemPublicId, content = "", sellerId = "", summary = "")
//
//        When("finding item by publicId") {
//            coEvery { itemRepository.findByPublicId(itemPublicId) } returns mockItem
//
//            Then("it should return the corresponding ItemResponse") {
//                val result = itemSearchService.findItemByPublicId(itemPublicId)
//                result.title shouldBe mockItem.title
//            }
//        }
//    }
//
//    Given("a valid categories") {
//        val mockCategory1 = ItemCategory(title = "Test Category 1", publicId = 1L, parentPublicId = 1L)
//        val mockCategory2 = ItemCategory(title = "Test Category 2", publicId = 2L, parentPublicId = 1L)
//        val mockCategory3 = ItemCategory(title = "Test Category 3", publicId = 3L, parentPublicId = 1L)
//        val mockItem1 = Item(title = "Test Item 1", categoryPublicId = 2L, publicId = 1L, content = "", sellerId = "", summary = "")
//        val mockItem2 = Item(title = "Test Item 2", categoryPublicId = 3L, publicId = 2L, content = "", sellerId = "", summary = "")
//
//
//        coEvery { itemCategoryRepository.findByParentPublicId(1L) } returns flowOf(mockCategory2, mockCategory3)
//        coEvery { itemCategoryRepository.findByParentPublicIdIn(listOf(2L, 3L)) } returns emptyFlow()
//        coEvery { itemRepository.findByCategoryPublicIdIn(listOf(1L, 2L, 3L)) } returns flowOf(mockItem1, mockItem2)
//
//        When("finding items by category publicId") {
//            coEvery { itemCategoryRepository.findByPublicId(1L) } returns mockCategory1
//            val result = itemSearchService.findItemsByCategoryPublicId(1L)
//
//            Then("it should return a list of corresponding ItemResponse") {
//                result.size shouldBe 2
//                result.map { it.title } shouldContain "Test Item 1"
//                result.map { it.title } shouldContain "Test Item 2"
//            }
//        }
//
//        When("finding items by category title") {
//            coEvery { itemCategoryRepository.findByTitle("Test Category 1") } returns mockCategory1
//            val result = itemSearchService.findItemsByCategoryTitle("Test Category 1")
//
//            Then("it should return a list of corresponding ItemResponse") {
//                result.size shouldBe 2
//                result.map { it.title } shouldContain "Test Item 1"
//                result.map { it.title } shouldContain "Test Item 2"
//            }
//        }
//    }
//
//})