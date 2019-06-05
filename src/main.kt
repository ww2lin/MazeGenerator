import javax.swing.JFrame
import java.awt.Dimension



fun main() {

    val dimen = 40
    val margin = 5
    val screenDimen = 400

    val maze = MazeGenerator(dimen)
    val view = MazeView(maze,
        screenDimen,
        5,
        margin,
        timeLapse = true)

    JFrame("MazeGenerator").apply{
        contentPane.preferredSize = Dimension(screenDimen + margin, screenDimen + margin)
        add(view)
        isVisible = true
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        pack()

    }
}
