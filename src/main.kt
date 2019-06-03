import javax.swing.JFrame

fun main() {
    val dimen = 1000
    val screenDimen = 1000

    val maze = MazeBoard(dimen)
    val view = MazeView(maze,
        screenDimen,
        5,
        5)

    JFrame("MazeGenerator").apply {
        setSize(screenDimen, screenDimen)
        contentPane.add(view)
        isVisible = true
    }
}
