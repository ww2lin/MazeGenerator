import java.awt.Image
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class ImageHelper {

    fun getBlackWhiteImage(imagePath: String, dimen: Int, imageType: Int): BufferedImage {
        val bufferedImage = ImageIO.read(File(imagePath))
        val image = bufferedImage.getScaledInstance(dimen, dimen, Image.SCALE_FAST)
        val blackWhite = BufferedImage(
            image.getWidth(null),
            image.getHeight(null),
            imageType
        )
        val g2d = blackWhite.createGraphics()
        g2d.drawImage(image, 0, 0, null)
        g2d.dispose()
        return blackWhite
    }


    fun imageToArray(image: BufferedImage): Array<IntArray> {
        val width = image.getWidth(null)
        val height = image.getHeight(null)

        val result = Array(width) { IntArray(height) { MazeGenerator.EMPTY } }

        for (i in 0 until image.getWidth(null)) {
            for (j in 0 until image.getHeight(null)) {
                result[i][j] = image.getRGB(i,j)
            }
        }

        return result
    }
}