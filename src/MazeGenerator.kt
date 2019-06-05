import java.util.Stack
import kotlin.random.Random
import kotlin.test.assertEquals

class MazeGenerator(val dimension: Int) {

    companion object {
        const val EMPTY = 0
        const val VISITED = 1
    }

    private val board = Array(dimension) { IntArray(dimension) { EMPTY } }

    // Keep track of starting and ending coordinate.
    private val start = mutableListOf<Pair<Int, Int>>()
    private val end = mutableListOf<Pair<Int, Int>>()

    init {
        val startRow = Random.nextInt(dimension)
        val startCol = Random.nextInt(dimension)
        constructBoard(Triple(startRow, startCol, null))
    }

    private fun constructBoard(initCoord: Triple<Int, Int, Pair<Int, Int>?>) {
        val stack = Stack<Triple<Int, Int, Pair<Int, Int>?>>()
        stack.push(initCoord)

        while(stack.isNotEmpty()) {
            val currentCell = stack.pop()
            val row = currentCell.first
            val col = currentCell.second
            val currentCoord = Pair(row, col)
            val previousCoord = currentCell.third

            if (!withinBounds(row, col) || board[row][col] == VISITED) {
                continue
            }

            board[row][col] = VISITED

            val next = arrayListOf(
                Triple(row + 1, col, currentCoord),
                Triple(row - 1, col, currentCoord),
                Triple(row, col + 1, currentCoord),
                Triple(row, col - 1, currentCoord)
            ).shuffled()

            if (previousCoord != null) {
                // keep track of the path the dfs is visiting.
                start.add(previousCoord)
                end.add(currentCoord)
            }

            for (triple in next) {
                stack.push(triple)
            }
        }
    }

    private fun withinBounds(row: Int, col: Int): Boolean {
        return row in 0 until dimension && col in 0 until dimension
    }

    fun getConnections() = sequence {
        assertEquals(start.size, end.size)
        val size = start.size
        for (i in 0 until size) {
            yield(Pair(start[i], end[i]))
        }
    }
}
