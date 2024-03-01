package sosteam.deamhome.domain.coupon.provider

class CouponRecommendProvider {
	companion object HungarianAlgorithm {
		private lateinit var matrix: Array<Array<Int>>
		private var matrixSize: Int = 0
		private lateinit var assignmentRows: IntArray
		private lateinit var occupiedCols: BooleanArray
		private var counter = 1
		
		fun apply(MatrixOriginal: Array<Array<Int>>): IntArray {
			matrix = MatrixOriginal.clone()
			matrixSize = MatrixOriginal.size
			assignmentRows = IntArray(matrixSize)
			occupiedCols = BooleanArray(matrixSize)
			return stepByStep()
		}
		
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
						(coveredColumns.contains(j) && coveredRows.contains(i)) ->
							matrix[i][j] += min * 2
						
						(coveredColumns.contains(j) || coveredRows.contains(i)) ->
							matrix[i][j] += min
					}
			
			min = Int.MAX_VALUE
			for (i in 0 until matrixSize)
				for (j in 0 until matrixSize)
					if (matrix[i][j] < min)
						min = matrix[i][j]
			for (i in 0 until matrixSize)
				for (j in 0 until matrixSize)
					matrix[i][j] -= min
			counter++
			
			if (tryToAssign(0))
				return
			
			return Step3()
		}
		
		private fun verticalOrHorizontal(row: Int, column: Int) =
			(compareValues(countZeroesInColumn(column), countZeroesInRow(row)))
		
		private fun tryToAssign(row: Int): Boolean {
			if (row == assignmentRows.size)
				return true
			for (column in 0 until assignmentRows.size) {
				if (matrix[row][column] == 0 && occupiedCols[column] == false) {
					assignmentRows[row] = column
					occupiedCols[column] = true
					if (tryToAssign(row + 1))
						return true
					occupiedCols[column] =
						false
				}
			}
			return false
		}
	}
}