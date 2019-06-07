import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JComponent


class Presenter {

    private val imageHelper = ImageHelper()

    fun loadImageAndRefresh(filePath: String, dimen: Int, mazeView: MazeView) {
        val image = imageHelper.getImage(filePath, dimen, BufferedImage.TYPE_INT_ARGB)
        val image2DArray = imageHelper.imageToArray(image)
        val maze = MazeGenerator(dimen, board = image2DArray)
        mazeView.reload(dimen, maze.getConnections().toList(), image2DArray, stepIncrementSize = dimen/10)
    }

    fun loadImageAsBlackAndWhiteThenRefresh(filePath: String, dimen: Int, mazeView: MazeView) {
        val image = imageHelper.getImage(filePath, dimen, BufferedImage.TYPE_BYTE_BINARY)
        val image2DArray = imageHelper.imageToBWArray(image)
        val maze = MazeGenerator(dimen, board = image2DArray)
        mazeView.reload(dimen, maze.getConnections().toList())
    }

    fun exportImage(exportDirectory: String, fileName: String, view: JComponent) {
        val img = BufferedImage(view.width, view.height, BufferedImage.TYPE_BYTE_GRAY)
        view.rootPane.contentPane.paint(img.createGraphics())
        val outputDir = File(exportDirectory + fileName + "_" + System.currentTimeMillis() + ".png")
        ImageIO.write(img, "png", outputDir)
    }

    fun newMaze(dimen: Int, mazeView: MazeView) {
        val maze = MazeGenerator(dimen)
        mazeView.reload(dimen, maze.getConnections().toList())
    }
}

