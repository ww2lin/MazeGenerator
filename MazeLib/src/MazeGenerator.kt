import java.util.Stack
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class MazeGenerator(
    val dimension: Int,
    private val board: Array<IntArray> = Array(dimension) { IntArray(dimension) { EMPTY } }
) {

    companion object {
        const val EMPTY = 0
        const val VISITED = 1
    }

    // Keep track of starting and ending coordinate.
    private val start = mutableListOf<Pair<Int, Int>>()
    private val end = mutableListOf<Pair<Int, Int>>()

    init {
        assertNotNull(board)
        assertNotNull(board[0])
        assertEquals(board.size, board[0].size)

        val positions: MutableList<Pair<Int, Int>> = mutableListOf()

        for (i in 0 until dimension) {
            for (j in 0 until dimension) {
                if (board[i][j] == EMPTY) {
                    positions.add(Pair(i, j))
                }
            }
        }
        // shuffle the available positions.
        positions.shuffle()
        val visitedSet = mutableSetOf<Pair<Int, Int>>()
        for (coord in positions) {
            constructBoard(Triple(coord.first, coord.second, null), visitedSet)
        }
    }

    private fun constructBoard(initCoord: Triple<Int, Int, Pair<Int, Int>?>, visitedSet: MutableSet<Pair<Int, Int>>) {
        val stack = Stack<Triple<Int, Int, Pair<Int, Int>?>>()
        stack.push(initCoord)

        while (stack.isNotEmpty()) {
            val currentCell = stack.pop()
            val row = currentCell.first
            val col = currentCell.second
            val currentCoord = Pair(row, col)
            val previousCoord = currentCell.third

            if (!withinBounds(row, col) || visitedSet.contains(currentCoord)) {
                continue
            }

            visitedSet.add(currentCoord)

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
