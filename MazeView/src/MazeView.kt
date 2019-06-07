import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JComponent
import kotlin.math.max
import kotlin.math.min

class MazeView(
    private var dimen: Int = 50,
    private val viewSize: Int = 500,
    private val thickness: Int = 5,
    private val margin: Int = 5,
    private val timeLapse: Boolean = false,
    private var stepIncrementSize: Int = 1,
    // list of coordinates - [pair<x1, y1>, pair<x2, y2>]
    private var connections: List<Pair<Pair<Int, Int>, Pair<Int, Int>>> = emptyList(),
    private var colorPallet: Array<IntArray> = emptyArray()
) : JComponent() {

    private var stepSize = 0
    private val colorCache = mutableMapOf<Int, Color>()

    init {
        reload(dimen, connections)
    }

    fun reload(
        dimen: Int,
        connections: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>,
        colorPallet: Array<IntArray> = emptyArray(),
        stepIncrementSize: Int = 1
    ) {
        this.connections = connections
        this.dimen = dimen
        this.colorPallet = colorPallet
        this.stepIncrementSize = stepIncrementSize
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
        val spacing = (viewSize.toFloat() / dimen)
        stepSize += stepIncrementSize
        connections
            .subList(0, min(stepSize, connections.size))
            .forEach {
                val start = it.first
                val end = it.second

                val x1 = (start.first * spacing + margin).toInt()
                val y1 = (start.second * spacing + margin).toInt()

                val x2 = (end.first * spacing + margin).toInt()
                val y2 = (end.second * spacing + margin).toInt()

                g2.color = getPaintColor(start, end)
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

    private fun getPaintColor(start: Pair<Int, Int>, end: Pair<Int, Int>): Color {
        if (colorPallet.isEmpty()) {
            return Color.BLACK
        }

        // TODO: take average of the two pixel colors
        val rgb1 = colorPallet[start.first][start.second]
        val rgb2 = colorPallet[end.first][end.second]

        return colorCache.getOrPut(rgb1, { Color(rgb1, true) })
    }
}