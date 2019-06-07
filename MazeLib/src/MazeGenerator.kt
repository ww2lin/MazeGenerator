import java.util.Stack
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class MazeGenerator(
    val dimension: Int,
    board: Array<IntArray> = Array(dimension) { IntArray(dimension) { EMPTY } }
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
        val visitedSet = Array(dimension) { IntArray(dimension) { EMPTY } }
        for (i in 0 until dimension) {
            for (j in 0 until dimension) {
                positions.add(Pair(i, j))
                if (board[i][j] == VISITED) {
                    visitedSet[i][j] = VISITED
                }
            }
        }
        // shuffle the available positions.
        positions.shuffle()

        for (coord in positions) {
            constructBoard(Triple(coord.first, coord.second, null), visitedSet)
        }
    }

    private fun constructBoard(initCoord: Triple<Int, Int, Pair<Int, Int>?>, visitedSet: Array<IntArray>) {
        val stack = Stack<Triple<Int, Int, Pair<Int, Int>?>>()
        stack.push(initCoord)

        while (stack.isNotEmpty()) {
            val (row, col, previousCoord) = stack.pop()
            val currentCoord = Pair(row, col)

            if (!withinBounds(row, col) || visitedSet[row][col] == VISITED) {
                continue
            }

            visitedSet[row][col] = VISITED

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
