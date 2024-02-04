package sosteam.deamhome.domain.coupon.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.coupon.entity.Coupon
import sosteam.deamhome.domain.coupon.entity.CouponDiscountType
import sosteam.deamhome.domain.coupon.entity.CouponType
import sosteam.deamhome.domain.coupon.handler.request.CouponSearchRequest
import sosteam.deamhome.domain.coupon.handler.response.CouponResponse
import sosteam.deamhome.domain.coupon.provider.CouponJunkProvider
import sosteam.deamhome.domain.coupon.provider.CouponRecommendProvider
import sosteam.deamhome.domain.item.entity.Item

@Service
class CouponSearchService {
	private val parent = mutableMapOf<Coupon, Coupon>()
	private val rank = mutableMapOf<Coupon, Int>()
	private var itemsSize: Int = 0
	private var couponsSize: Int = 0
	
	suspend fun searchCoupons(
		request: CouponSearchRequest,
		ableCoupons: List<Coupon>,
		items: List<Item>
	): List<Map<String, CouponResponse>> {
		itemsSize = items.size
		couponsSize = ableCoupons.size
		
		val dividedCoupons = divideCoupon(ableCoupons, items)
		
		return dividedCoupons.flatMap { group ->
			val filteredItems = items.filter { it.publicId in group.value }
			optimizeCoupons(group.key, filteredItems).map { (itemPublicId, coupon) ->
				mapOf(itemPublicId to CouponResponse.fromCoupon(coupon))
			}
		}
	}
	
	private fun divideCoupon(coupons: List<Coupon>, items: List<Item>): Map<List<Coupon>, List<String>> {
		initializeUnionFind(coupons)
		
		if (coupons.any {
				it.couponType == CouponType.USER_SPECIFIC
						|| it.couponType == CouponType.MIN_PURCHASE_AMOUNT
						|| it.couponType == CouponType.BUNDLE_DISCOUNT
						|| it.couponType == CouponType.SPECIFIC_LINK
			}) {
			coupons.forEach { coupon -> parent[coupon] = coupons.first() }
		} else {
			coupons.forEach { coupon1 ->
				coupons.forEach { coupon2 ->
					if (coupon1 != coupon2
						&& ((coupon1.couponType == CouponType.PRODUCT_SPECIFIC
								&& coupon2.couponType == CouponType.PRODUCT_SPECIFIC
								&& coupon1.itemIds.any { coupon2.itemIds.contains(it) })
								|| (coupon1.couponType == CouponType.CATEGORY_SPECIFIC
								&& coupon2.couponType == CouponType.CATEGORY_SPECIFIC
								&& coupon1.categoryIds.any { coupon2.categoryIds.contains(it) })
								|| (coupon1.couponType == CouponType.PRODUCT_SPECIFIC
								&& coupon2.couponType == CouponType.CATEGORY_SPECIFIC
								&& coupon1.itemIds.any { itemId ->
							items.any { item -> item.publicId == itemId && coupon2.categoryIds.contains(item.categoryPublicId) }
						}))
					) {
						unionRoot(coupon1, coupon2)
					}
				}
			}
		}
		
		return coupons.groupBy { findRoot(it) }
			.map { entry ->
				entry.value to entry.value.flatMap { it.itemIds }.filterNotNull().distinct()
			}.toMap()
	}
	
	private suspend fun optimizeCoupons(coupons: List<Coupon>, items: List<Item>): Map<String, Coupon> {
		val itemsColumn = items.toMutableList()
		val couponsRow = coupons.toMutableList()
		
		// 1:1로 대응되는 행렬 생성
		while (couponsRow.size > itemsColumn.size) {
			itemsColumn.add(CouponJunkProvider.createJunkItem())
		}
		while (couponsRow.size < itemsColumn.size) {
			couponsRow.add(CouponJunkProvider.createJunkCoupon())
		}
		val costMatrix = Array(itemsColumn.size) { Array(couponsRow.size) { 0 } }
		
		for (i in couponsRow.indices) {
			for (j in itemsColumn.indices) {
				val coupon = couponsRow[i]
				val item = itemsColumn[j]
				
				if (
					((coupon.couponType == CouponType.PRODUCT_SPECIFIC
							|| coupon.couponType == CouponType.BUNDLE_DISCOUNT)
							&& !coupon.itemIds.contains(item.publicId))
					|| (coupon.couponType == CouponType.CATEGORY_SPECIFIC && !coupon.categoryIds.contains(item.categoryPublicId))
				) {
					costMatrix[i][j] = 112345678
					continue
				}
				costMatrix[i][j] = when (coupon.couponDiscountType) {
					CouponDiscountType.PERCENTAGE_DISCOUNT -> (item.price - item.price * (coupon.discount / 100.0)).toInt()
					CouponDiscountType.FIXED_AMOUNT_DISCOUNT -> item.price - coupon.discount
				}
			}
		}
		
		// 헝가리안 알고리즘
		val result = CouponRecommendProvider.apply(costMatrix)
		
		val optimizedAssignments = mutableMapOf<String, Coupon>()
		result.forEachIndexed { index, assignedIndex ->
			val coupon = couponsRow[index]
			val item = itemsColumn[assignedIndex]
			
			if (coupon.id != null && item.id != null) {
				when {
					itemsSize <= couponsSize -> optimizedAssignments[item.publicId] = coupon
					itemsSize > couponsSize -> {
						if (isAbleCoupon(coupon, item.publicId)) {
							optimizedAssignments[item.publicId] = coupon
						}
					}
				}
			}
		}
		
		return optimizedAssignments
	}
	
	private fun isAbleCoupon(coupon: Coupon, itemId: String): Boolean {
		if (coupon.couponType == CouponType.USER_SPECIFIC) {
			return true
		}
		return false
	}
	
	private fun initializeUnionFind(coupons: List<Coupon>) {
		coupons.forEach { coupon ->
			parent[coupon] = coupon
			rank[coupon] = 0
		}
	}
	
	private fun findRoot(x: Coupon): Coupon {
		if (parent[x] != x) {
			parent[x] = findRoot(parent[x]!!)
		}
		return parent[x]!!
	}
	
	private fun unionRoot(x: Coupon, y: Coupon) {
		val rootX = findRoot(x)
		val rootY = findRoot(y)
		
		if (rootX != rootY) {
			when {
				rank[rootX]!! < rank[rootY]!! -> parent[rootX] = rootY
				rank[rootX]!! > rank[rootY]!! -> parent[rootY] = rootX
				else -> {
					parent[rootY] = rootX
					rank[rootX] = rank[rootX]!! + 1
				}
			}
		}
	}
}