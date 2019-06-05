import java.awt.BasicStroke
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JComponent

class MazeView(
    private val maze: MazeGenerator,
    private val viewSize: Int,
    private val thickness: Int,
    private val margin: Int
) : JComponent() {

    public override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        val g2 = g as Graphics2D
        g2.stroke = BasicStroke(thickness.toFloat())

        val spacing = (viewSize / maze.dimension) + margin
        maze.getConnections().forEach {
            val start = it.first
            val end = it.second

            val x1 = start.first
            val y1 = start.second

            val x2 = end.first
            val y2 = end.second

            g2.drawLine(
                x1 * spacing,
                y1 * spacing,
                x2 * spacing,
                y2 * spacing
            )
        }
    }
}