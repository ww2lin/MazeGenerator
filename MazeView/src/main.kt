import javax.swing.JFrame
import java.awt.Dimension
import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import javax.swing.JFileChooser
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.JOptionPane

fun main() {
    val dimen = 100
    val margin = 5
    val screenDimen = 600
    val lineThickness = 5

    val maze = MazeGenerator(dimen)
    val view = MazeView(
        maze,
        screenDimen,
        lineThickness,
        margin,
        timeLapse = true
    )

    val importImageMenu = createImportMenuButton { filepath -> loadImageAndRefresh(filepath, dimen, view) }
    val exportImageMenu = createExportMenuButton { filePath, fileName ->  exportImage(filePath, fileName, view)}
    val newRandomMaze = createNewRandomMazeButton { view.reload(MazeGenerator(dimen)) }
    val aboutMe = createAboutMenuButton()

    val menuBar = JMenuBar().apply {
        add(JMenu("Menu").apply {
            add(importImageMenu)
            add(exportImageMenu)
            add(newRandomMaze)
            add(aboutMe)
        })
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


fun createImportMenuButton(onFileImport: (filePath: String) -> Unit): JMenuItem {
    return JMenuItem("Import image File").apply {
        addActionListener {
            val dialog = FileDialog(null as? Frame, "Select File to Open").apply {
                mode = FileDialog.LOAD
                isVisible = true
            }
            if (dialog.file != null) {
                onFileImport(dialog.directory + dialog.file.toString())
            }
        }
    }
}

fun createNewRandomMazeButton(onClick: () -> Unit): JMenuItem {
    return JMenuItem("New Random Maze").apply {
        addActionListener {
            onClick()
        }
    }
}

fun createExportMenuButton(onFileExport: (filePath: String, fileName: String) -> Unit): JMenuItem {
    return JMenuItem("Export image File").apply {
        addActionListener {
            val dialog = FileDialog(null as? Frame, "Select File to Open").apply {
                mode = FileDialog.SAVE
                isVisible = true
            }
            if (dialog.file != null) {
                onFileExport(dialog.directory, dialog.file)
            }
        }
    }
}

fun createAboutMenuButton(): JMenuItem {
    return JMenuItem("About").apply {
        addActionListener {
            val text = """
                        This is a simple maze generation program.
                        You can either let it random generate or have it generate from an image.

                        for more details please visit: https://github.com/ww2lin/MazeGenerator
                        """.trimIndent()
            JOptionPane.showMessageDialog(null, text, "About", JOptionPane.PLAIN_MESSAGE, null)
        }
    }
}
