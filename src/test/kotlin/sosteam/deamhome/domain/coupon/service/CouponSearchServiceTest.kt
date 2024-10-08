package sosteam.deamhome.domain.coupon.service

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import sosteam.deamhome.domain.coupon.entity.Coupon
import sosteam.deamhome.domain.coupon.entity.CouponDiscountType
import sosteam.deamhome.domain.coupon.entity.CouponType
import sosteam.deamhome.domain.coupon.handler.request.CouponSearchRequest
import sosteam.deamhome.domain.coupon.handler.response.CouponResponse
import sosteam.deamhome.domain.coupon.repository.CouponRepository
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.domain.item.entity.ItemStatus
import java.time.OffsetDateTime

class CouponSearchServiceTest : BehaviorSpec({
	isolationMode = IsolationMode.InstancePerLeaf
	val couponRepository = mockk<CouponRepository>()
	val itemRepository = mockk<ItemRepository>()
	val couponSearchService = CouponSearchService()
	lateinit var coupon1: Coupon
	lateinit var coupon2: Coupon
	lateinit var coupon3: Coupon
	lateinit var item1: Item
	lateinit var item2: Item
	lateinit var item3: Item
	lateinit var searchRequest: CouponSearchRequest
	
	Given("상품과 쿠폰이 주어진다") {
		val coupons = listOf(coupon1, coupon2, coupon3)
		val items = listOf(item1, item2, item3)
		val request = searchRequest
		
		coEvery { couponRepository.findCoupons(any(), any(), any(), any(), any()) } returns coupons.asFlow()
		coEvery { itemRepository.findByPublicId(any()) } returnsMany items
		coEvery { itemRepository.findByCategoryPublicIdIn(any()) } returns flowOf()
		
		When("최적의 쿠폰을 추천받는다") {
			val itemIds = items.map { it.publicId }
			val result = couponSearchService.searchCoupons(request, coupons, items)
			println(result)
			
			Then("결과를 확인한다") {
				val expectedResults = listOf(
					mapOf("item2" to CouponResponse.fromCoupon(coupon1)),
					mapOf("item3" to CouponResponse.fromCoupon(coupon2)),
					mapOf("item1" to CouponResponse.fromCoupon(coupon3))
				)
				println(expectedResults)
				result[0].coupon.publicId shouldBe expectedResults[0]["item2"]?.publicId
			}
		}
	}
	
	beforeTest {
		coupon1 = Coupon(
			id = 1L,
			publicId = "coupon1",
			title = "10% Off",
			content = "10% off",
			couponType = CouponType.PRODUCT_SPECIFIC,
			couponDiscountType = CouponDiscountType.PERCENTAGE_DISCOUNT,
			userId = null,
			itemIds = listOf("item1", "item2"),
			categoryIds = listOf(),
			status = true,
			startDate = OffsetDateTime.now(),
			endDate = OffsetDateTime.now().plusDays(10),
			discount = 10,
			minPurchaseAmount = null,
			links = listOf()
		)
		coupon2 = Coupon(
			id = 2L,
			publicId = "coupon2",
			title = "20% Off",
			content = "20% off",
			couponType = CouponType.PRODUCT_SPECIFIC,
			couponDiscountType = CouponDiscountType.PERCENTAGE_DISCOUNT,
			userId = null,
			itemIds = listOf("item2", "item3"),
			categoryIds = listOf(),
			status = true,
			startDate = OffsetDateTime.now(),
			endDate = OffsetDateTime.now().plusDays(10),
			discount = 20,
			minPurchaseAmount = null,
			links = listOf()
		)
		coupon3 = Coupon(
			id = 3L,
			publicId = "coupon3",
			title = "1500 discount",
			content = "1500 discount",
			couponType = CouponType.PRODUCT_SPECIFIC,
			couponDiscountType = CouponDiscountType.FIXED_AMOUNT_DISCOUNT,
			userId = null,
			itemIds = listOf("item1"),
			categoryIds = listOf(),
			status = true,
			startDate = OffsetDateTime.now(),
			endDate = OffsetDateTime.now().plusDays(10),
			discount = 1500,
			minPurchaseAmount = null,
			links = listOf()
		)
		item1 = Item(
			id = 1L,
			publicId = "item1",
			categoryPublicId = "cat1",
			title = "Item1",
			content = "item",
			summary = "item",
			price = 1000,
			storeId = "seller123",
			freeDelivery = false,
			reviewScore = listOf(0, 0, 0, 0, 0),
			option = listOf(),
			productNumber = "0",
			deadline = OffsetDateTime.MAX,
			originalWork = "",
			material = "",
			size = "",
			weight = "",
			shippingCost = 0,
			status = ItemStatus.AVAILABLE
		)
		item2 = Item(
			id = 2L,
			publicId = "item2",
			categoryPublicId = "cat1",
			title = "Item2",
			content = "item",
			summary = "item",
			price = 1000,
			storeId = "seller123",
			freeDelivery = false,
			reviewScore = listOf(0, 0, 0, 0, 0),
			option = listOf(),
			productNumber = "0",
			deadline = OffsetDateTime.MAX,
			originalWork = "",
			material = "",
			size = "",
			weight = "",
			shippingCost = 0,
			status = ItemStatus.AVAILABLE
		)
		item3 = Item(
			id = 3L,
			publicId = "item3",
			categoryPublicId = "cat1",
			title = "Item3",
			content = "item",
			summary = "item",
			price = 1000,
			storeId = "seller123",
			freeDelivery = false,
			reviewScore = listOf(0, 0, 0, 0, 0),
			option = listOf(),
			productNumber = "0",
			deadline = OffsetDateTime.MAX,
			originalWork = "",
			material = "",
			size = "",
			weight = "",
			shippingCost = 0,
			status = ItemStatus.AVAILABLE
		)
		searchRequest = CouponSearchRequest(
			userId = "user123",
			itemIds = listOf("item1", "item2", "item3"),
			links = listOf()
		)
	}
})