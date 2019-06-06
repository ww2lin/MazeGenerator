import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JComponent

fun loadImageAndRefresh(filePath: String, dimen: Int, mazeView: MazeView) {
    val image = getBlackWhiteImage(filePath, dimen)
    val maze = MazeGenerator(dimen, board = imageToArray(image))
    mazeView.reload(maze)
}

fun exportImage(exportDirectory: String, fileName: String, view: JComponent) {
    val img = BufferedImage(view.width, view.height, BufferedImage.TYPE_BYTE_GRAY)
    view.rootPane.contentPane.paint(img.createGraphics())
    val outputDir = File(exportDirectory + fileName + "_" + System.currentTimeMillis() + ".png")
    ImageIO.write(img, "png", outputDir)
}

