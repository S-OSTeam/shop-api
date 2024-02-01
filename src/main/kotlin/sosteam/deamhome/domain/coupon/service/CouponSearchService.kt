package sosteam.deamhome.domain.coupon.service

import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.coupon.entity.Coupon
import sosteam.deamhome.domain.coupon.entity.CouponDiscountType
import sosteam.deamhome.domain.coupon.entity.CouponType
import sosteam.deamhome.domain.coupon.handler.request.CouponSearchRequest
import sosteam.deamhome.domain.coupon.handler.response.CouponResponse
import sosteam.deamhome.domain.coupon.repository.CouponRepository
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.exception.ItemNotFoundException
import sosteam.deamhome.domain.item.repository.ItemRepository
import java.time.LocalDateTime

@Service
class CouponSearchService(
	private val couponRepository: CouponRepository,
	private val itemRepository: ItemRepository
) {
	private val parent = mutableMapOf<Coupon, Coupon>()
	private val rank = mutableMapOf<Coupon, Int>()
	
	suspend fun searchCoupons(request: CouponSearchRequest): List<Map<String, CouponResponse>> {
		val itemsByCategory = itemRepository.findByCategoryPublicIdIn(request.categoryIds).toList()
		val categoryItemIds = itemsByCategory.map { it.publicId }
		val items = request.itemIds + categoryItemIds
		
		val ableCoupons = couponRepository.findCoupons(request.userId, items).toList()
		val dividedCoupons = divideCoupon(ableCoupons)
		return dividedCoupons.map { group ->
			optimizeCoupons(group.key, group.value).map { (itemPublicId, coupon) ->
				mapOf(itemPublicId to CouponResponse.fromCoupon(coupon))
			}
		}.flatten()
	}
	
	private fun divideCoupon(coupons: List<Coupon>): Map<List<Coupon>, List<String>> {
		initializeUnionFind(coupons)
		
		coupons.forEach { coupon1 ->
			coupons.forEach { coupon2 ->
				if (coupon1 != coupon2
					&& (coupon1.itemIds.any { coupon2.itemIds.contains(it) }
							|| coupon1.couponType == CouponType.USER_SPECIFIC
							|| coupon2.couponType == CouponType.USER_SPECIFIC)
				) {
					unionRoot(coupon1, coupon2)
				}
			}
		}
		
		return coupons.groupBy { findRoot(it) }
			.map { entry ->
				entry.value to entry.value.flatMap { it.itemIds }.filterNotNull().distinct()
			}.toMap()
	}
	
	private suspend fun optimizeCoupons(coupons: List<Coupon>, itemIds: List<String>): Map<String, Coupon> {
		val items = itemIds.map { itemRepository.findByPublicId(it) ?: throw ItemNotFoundException() }
		val itemsColumn = items.toMutableList()
		val couponsRow = coupons.toMutableList()
		
		// 1:1로 대응되는 행렬 생성
		while (couponsRow.size > itemsColumn.size) {
			itemsColumn.add(createJunkItem())
		}
		while (couponsRow.size < itemsColumn.size) {
			couponsRow.add(createJunkCoupon())
		}
		val costMatrix = Array(itemsColumn.size) { Array(couponsRow.size) { 0 } }
		
		for (i in couponsRow.indices) {
			for (j in itemsColumn.indices) {
				val coupon = couponsRow[i]
				val item = itemsColumn[j]
				
				if (coupon.couponType == CouponType.PRODUCT_SPECIFIC && !coupon.itemIds.contains(item.publicId)) {
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
		val hungarianAlgorithm = HungarianAlgorithm(costMatrix)
		val result = hungarianAlgorithm.stepByStep()
		
		val optimizedAssignments = mutableMapOf<String, Coupon>()
		result.forEachIndexed { index, assignedIndex ->
			val coupon = couponsRow[index]
			val item = itemsColumn[assignedIndex]
			
			if (coupon.id != null && item.id != null) {
				optimizedAssignments[item.publicId] = coupon
			}
		}
		
		return optimizedAssignments
	}
	
	private class HungarianAlgorithm(private val MatrixOriginal: Array<Array<Int>>) {
		private val matrixSize = MatrixOriginal.size
		private var matrix = MatrixOriginal.clone()
		private var counter = 1
		private val assignmentRows = IntArray(matrixSize)
		private val occupiedCols = BooleanArray(matrixSize)
		
		fun stepByStep(): IntArray {
			if (tryToAssign(0))
				return assignmentRows
			Step1()
			if (tryToAssign(0))
				return assignmentRows
			Step2()
			if (tryToAssign(0))
				return assignmentRows
			Step3()
			return assignmentRows
		}
		
		private fun Step1() {
			var min: Int
			for (i in 0 until matrixSize) {
				min = matrix[i][0]
				for (j in 1 until matrixSize)
					if (matrix[i][j] < min)
						min = matrix[i][j]
				for (j in 0 until matrixSize)
					matrix[i][j] -= min
			}
		}
		
		private fun Step2() {
			var min: Int
			for (j in 0 until matrixSize) {
				min = matrix[0][j]
				for (i in 1 until matrixSize)
					if (matrix[i][j] < min)
						min = matrix[i][j]
				for (i in 0 until matrixSize)
					matrix[i][j] -= min
			}
		}
		
		private fun countZeroesInRow(i: Int) = matrix[i].count { it == 0 }
		
		private fun countZeroesInColumn(j: Int): Int {
			var result = 0
			for (i in 0 until matrixSize)
				if (matrix[i][j] == 0)
					result++
			return result
		}
		
		private fun Step3() {
			val coveredColumns = mutableListOf<Int>()
			val coveredRows = mutableListOf<Int>()
			var min = Int.MAX_VALUE
			var VerHor: Int
			
			//counting zeroes
			for (row in 0 until matrixSize)
				for (column in 0 until matrixSize)
					if (matrix[row][column] == 0 && !(coveredColumns.contains(column) || coveredRows.contains(row))) {
						VerHor = verticalOrHorizontal(row, column)
						if (VerHor > 0)
							coveredColumns.add(column)
						else
							coveredRows.add(row)
					}
			
			for (i in 0 until matrixSize)
				for (j in 0 until matrixSize) {
					if (!((coveredColumns.contains(j) || coveredRows.contains(i))))
						if (matrix[i][j] < min)
							min = matrix[i][j]
				}
			
			
			for (i in 0 until matrixSize)
				for (j in 0 until matrixSize)
					when {
						(coveredColumns.contains(j) && coveredRows.contains(i)) -> //if zero covered twice
							matrix[i][j] += min * 2
						
						(coveredColumns.contains(j) || coveredRows.contains(i)) ->
							matrix[i][j] += min
					}
			
			min = Int.MAX_VALUE
			for (i in 0 until matrixSize)
				for (j in 0 until matrixSize)
					if (matrix[i][j] < min)
						min = matrix[i][j]
			for (i in 0 until matrixSize)  //Subtract the minimum element
				for (j in 0 until matrixSize)
					matrix[i][j] -= min
			counter++
			
			if (tryToAssign(0))
				return
			
			return Step3()
		}
		
		private fun verticalOrHorizontal(row: Int, column: Int) =
			(compareValues(countZeroesInColumn(column), countZeroesInRow(row))) //check where is more zeroes per line
		
		private fun tryToAssign(row: Int): Boolean { //"row" - number of row to assign
			if (row == assignmentRows.size) // If all rows were assigned a cell; assignmentRows.size==matrix.size
				return true
			for (column in 0 until assignmentRows.size) {
				if (matrix[row][column] == 0 && occupiedCols[column] == false) {
					assignmentRows[row] = column// Assign the current row the current column cell
					occupiedCols[column] = true // Mark the column as reserved
					if (tryToAssign(row + 1)) // If the next rows were assigned successfully a cell from a unique column, return true
						return true
					occupiedCols[column] =
						false // If the next rows were not able to get a cell, go back and try for the previous rows another cell from another column
				}
			}
			return false
		}
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
	
	private fun createJunkItem(): Item {
		val PRICE = 112345678
		return Item(
			id = null,
			publicId = "",
			categoryPublicId = "",
			title = "",
			content = "",
			summary = "",
			price = PRICE,
			sellCnt = 0,
			wishCnt = 0,
			clickCnt = 0,
			stockCnt = 0,
			avgReview = 0.0,
			reviewCnt = 0,
			qnaCnt = 0,
			status = false,
			sellerId = "",
			freeDelivery = false
		)
	}
	
	private fun createJunkCoupon(): Coupon {
		val DISCOUNT = -112345678
		return Coupon(
			id = null,
			publicId = "",
			couponNumber = "",
			title = "",
			content = "",
			couponType = CouponType.USER_SPECIFIC,
			couponDiscountType = CouponDiscountType.FIXED_AMOUNT_DISCOUNT,
			userId = "",
			itemIds = listOf(),
			categoryIds = listOf(),
			status = false,
			startDate = LocalDateTime.now(),
			endDate = LocalDateTime.now(),
			discount = DISCOUNT,
			minPurchaseAmount = 0,
		)
	}
}