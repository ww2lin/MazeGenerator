import java.awt.BasicStroke
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JComponent
import kotlin.math.max
import kotlin.math.min

class MazeView(
    private var maze: MazeGenerator,
    private val viewSize: Int,
    private val thickness: Int,
    private val margin: Int,
    private val timeLapse: Boolean = false
) : JComponent() {


    private lateinit var connections: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>
    private var stepSize = 0

    init {
        reload(maze)
    }


    fun reload(newMaze: MazeGenerator) {
        connections = newMaze.getConnections().toList()
        stepSize = when (timeLapse) {
            true -> 0
            else -> connections.size
        }
        repaint()
    }

    public override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        val g2 = g as Graphics2D
        g2.stroke = BasicStroke(thickness.toFloat())

        // avoid integer truncation
        val spacing = (viewSize * 1.0 / maze.dimension)

        connections
            .subList(0, min(++stepSize, connections.size))
            .forEach {
                val start = it.first
                val end = it.second

                val x1 = (start.first * spacing + margin).toInt()
                val y1 = (start.second * spacing + margin).toInt()

                val x2 = (end.first * spacing + margin).toInt()
                val y2 = (end.second * spacing + margin).toInt()

                g2.drawLine(x1, y1, x2, y2)

                // get area to repaint
                val minX = min(x1, x2)
                val minY = min(y1, y2)
                val maxX = max(x1, x2)
                val maxY = max(y1, y2)


                if (stepSize < connections.size) {
                    repaint(
                        0,
                        minX - margin,
                        minY - margin,
                        maxX - minX + margin + thickness,
                        maxY - minY + margin + thickness
                    )
                }

            }
    }
}