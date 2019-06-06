import javax.swing.JFrame
import java.awt.Dimension
import java.awt.FileDialog
import java.awt.Frame
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JOptionPane
import javax.swing.event.MenuEvent
import javax.swing.event.MenuListener


fun main() {
    val dimen = 100
    val margin = 5
    val screenDimen = 600

    val maze = MazeGenerator(dimen)
    val view = MazeView(
        maze,
        screenDimen,
        5,
        margin,
        timeLapse = true
    )

    val importImageMenu = createImportMenuButton { filepath -> loadImageAndRefresh(filepath, dimen, view) }
    val aboutMe = createAboutMeMenuButton()

    val menuBar = JMenuBar().apply {
        add(importImageMenu)
        add(aboutMe)
    }

    JFrame("MazeGenerator").apply {
        contentPane.preferredSize = Dimension(screenDimen + margin, screenDimen + margin)
        add(view)
        isVisible = true
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        jMenuBar = menuBar
        pack()

    }
}

fun loadImageAndRefresh(filePath: String, dimen: Int, mazeView: MazeView) {
    val image = getBlackWhiteImage(filePath, dimen)
    val maze = MazeGenerator(dimen, board = imageToArray(image))
    mazeView.reload(maze)
}

fun createImportMenuButton(onFileImport: (filePath: String) -> Unit): JMenu {
    return JMenu("Import image File").apply {
        addMenuListener(object : MenuListener {
            override fun menuSelected(e: MenuEvent?) {
                val dialog = FileDialog(null as? Frame, "Select File to Open").apply {
                    mode = FileDialog.LOAD
                    isVisible = true
                }
                if (dialog.file != null) {
                    onFileImport(dialog.directory + dialog.file.toString())
                }
            }

            override fun menuCanceled(e: MenuEvent?) {
            }

            override fun menuDeselected(e: MenuEvent?) {
            }
        })
    }
}

fun createAboutMeMenuButton(): JMenu {
    return JMenu("About").apply {
        addMenuListener(object : MenuListener {
            override fun menuSelected(e: MenuEvent?) {
                val text = """
                        This is a simple maze generation program.
                        You can either let it random generate or have it generate from an image.

                        for more details please visit: https://github.com/ww2lin/MazeGenerator
                    """.trimIndent()
                JOptionPane.showMessageDialog(null, text, "About", JOptionPane.PLAIN_MESSAGE, null)
            }

            override fun menuCanceled(e: MenuEvent?) {
            }

            override fun menuDeselected(e: MenuEvent?) {
            }
        })
    }
}

