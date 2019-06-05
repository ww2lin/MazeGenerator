import javax.swing.JFrame

fun main() {
    val dimen = 50
    val screenDimen = 500

    val maze = MazeGenerator(dimen)
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
