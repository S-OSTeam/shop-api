package sosteam.deamhome.domain.coupon.service

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.asFlow
import sosteam.deamhome.domain.coupon.entity.Coupon
import sosteam.deamhome.domain.coupon.entity.CouponType
import sosteam.deamhome.domain.coupon.handler.request.CouponSearchRequest
import sosteam.deamhome.domain.coupon.handler.response.CouponResponse
import sosteam.deamhome.domain.coupon.repository.CouponRepository
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.repository.ItemRepository
import java.time.LocalDateTime

class CouponSearchServiceTest : BehaviorSpec({
	isolationMode = IsolationMode.InstancePerLeaf
	val couponRepository = mockk<CouponRepository>()
	val itemRepository = mockk<ItemRepository>()
	val couponSearchService = CouponSearchService(couponRepository, itemRepository)
	
	Given("만원 짜리 아이템 할인에 대해 쿠폰 적용에 따른 리스트 반환") {
		val request = CouponSearchRequest(userId = "user123", itemId = "item123")
		val mockItem = Item(
			id = 1L,
			publicId = "item123",
			categoryPublicId = "cat123",
			title = "Test Item",
			content = "Test Content",
			summary = "Test Summary",
			price = 10000,
			sellCnt = 0,
			wishCnt = 0,
			clickCnt = 0,
			stockCnt = 5,
			avgReview = 4.5,
			reviewCnt = 10,
			qnaCnt = 5,
			status = true,
			sellerId = "seller123",
			freeDelivery = true
		)
		val coupon1 = Coupon(
			id = 1L,
			publicId = "coupon123",
			title = "user 10%",
			content = "10% 할인",
			couponType = CouponType.PERCENTAGE_DISCOUNT,
			userId = "user123",
			itemId = null,
			status = true,
			startDate = LocalDateTime.now(),
			endDate = LocalDateTime.now().plusDays(10),
			discount = 10
		)
		val coupon2 = Coupon(
			id = 2L,
			publicId = "coupon456",
			title = "2000원",
			content = "첫 구매시 2000원 할인",
			couponType = CouponType.FIXED_AMOUNT_DISCOUNT,
			userId = null,
			itemId = "item123",
			status = true,
			startDate = LocalDateTime.now(),
			endDate = LocalDateTime.now().plusDays(5),
			discount = 2000
		)
		
		val mockCoupons = listOf(coupon1, coupon2)
		
		val expectedSortedCoupons = listOf(coupon2, coupon1).map { CouponResponse.fromCoupon(it) }
		
		coEvery { couponRepository.findCoupons(any(), any()) } returns mockCoupons.asFlow()
		coEvery { itemRepository.findByPublicId(any()) } returns mockItem
		
		When("쿠폰 검색") {
			val result = couponSearchService.searchCoupons(request)
			
			Then("2000원 할인 쿠폰이 먼저") {
				result.get(0).itemId shouldBe expectedSortedCoupons.get(0).itemId
				result.get(1).itemId shouldBe expectedSortedCoupons.get(1).itemId
			}
		}
	}
})