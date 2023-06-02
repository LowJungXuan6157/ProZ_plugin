@file:OptIn(DelicateCoroutinesApi::class)

package com.lowjungxuan.proz.jsontodart.ui

import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.lowjungxuan.proz.jsontodart.generator.ClazzGenerator
import com.lowjungxuan.proz.jsontodart.utils.JSONUtils
import com.lowjungxuan.proz.jsontodart.utils.createFileName
import kotlinx.coroutines.DelicateCoroutinesApi
import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.*

private const val PADDING = 10

class Model(val className: String, val fileName: String)

/**
 * @param project
 * @param input file with JSON
 * @param model proposed className & fileName
 * @param onGenerate user tap generate (dialog will be closed)
 */
class GeneratorDialog(
    private val project: Project,
    private val input: PsiFile,
    private val model: Model? = null,
    private val onGenerate: (fileName: String, code: String, options: Int, base: String) -> Unit
) {
    private val frame: JFrame
    private val validator = JSONUtils()
    private val editor = TextEditorProvider.getInstance().createEditor(project, input.virtualFile)
    private val radioButton1 = JRadioButton("With ProZ SDK", true) // true makes this button selected by default
    private val radioButton2 = JRadioButton("With BaseResponse.dart")
    private val radioButton3 = JRadioButton("Create BaseResponse.dart")

    init {
        frame = JFrame("Create dart model class for serializing/deserializing JSON").apply {
            val root = FocusManager.getCurrentManager().activeWindow
            setSize(root?.let { (it.width * 0.65).toInt() } ?: 700, root?.let { (it.height * 0.65).toInt() } ?: 520)
            defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
            add(build())
            isVisible = true
            setLocationRelativeTo(root)
        }
        frame.addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                dispose()
            }
        })
    }

    private fun build(): JComponent {
        return JPanel().apply {
            layout = BorderLayout()
            border = BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING)
            placeComponents(this)
        }
    }

    private fun prettify(postAction: () -> Unit) {
        validator.prettify { prettyJson ->
            (editor as? TextEditor)?.editor?.let { editor ->
                SwingUtilities.invokeAndWait {
                    editor.document.setText(prettyJson)
                    postAction()
                }
            }
        }
    }

    private fun placeComponents(panel: JPanel) = panel.apply {
        lateinit var classNameField: JTextField

        val button = JButton("Generate ${model?.className ?: ""}").apply {
            isEnabled = false
            addActionListener {
                if (classNameField.text.isEmpty()) {
                    JOptionPane.showMessageDialog(
                        frame,
                        "Please input class name",
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE
                    )
                    classNameField.requestFocus()
                    return@addActionListener
                }
                val selectedOption = when {
                    radioButton1.isSelected -> 1
                    radioButton2.isSelected -> 2
                    radioButton3.isSelected -> 3
                    else -> 1
                }
                val model = this@GeneratorDialog.model ?: Model(
                    className = classNameField.text,
                    fileName = createFileName(classNameField.text)
                )

                val code = ClazzGenerator().generate(model.className, input.text, selectedOption)
                val base = ClazzGenerator().generateBase()
                onGenerate(model.fileName, code, selectedOption, base)

                frame.dispose()
            }
        }

        classNameField = JTextField().apply {
            text = model?.className ?: ""
            if (model != null) isEnabled = false
            document.addDocumentListener(object : javax.swing.event.DocumentListener {
                override fun insertUpdate(p0: javax.swing.event.DocumentEvent?) = updateGeneratorButton()
                override fun removeUpdate(p0: javax.swing.event.DocumentEvent?) = updateGeneratorButton()
                override fun changedUpdate(p0: javax.swing.event.DocumentEvent?) = updateGeneratorButton()

                private fun updateGeneratorButton() {
                    button.text = "Generate ${classNameField.text}"
                }
            })
        }

        val formatButton = JButton("Format").apply {
            isEnabled = false
            addActionListener {
                isEnabled = false
                prettify {
                    isEnabled = true
                }
            }
        }

        add(editor.component, BorderLayout.CENTER)

        add(JPanel().apply {
            layout = BorderLayout()
            border = BorderFactory.createEmptyBorder(PADDING, 0, 0, 0)
            add(formatButton, BorderLayout.WEST)
            add(button, BorderLayout.CENTER)
        }, BorderLayout.SOUTH)

        add(JPanel().apply {
            layout = BorderLayout().apply {
                hgap = PADDING
            }
            border = BorderFactory.createEmptyBorder(0, 0, PADDING, 0)
            add(JLabel("Dart class name:"), BorderLayout.WEST)
            add(classNameField, BorderLayout.CENTER)

            add(JPanel().apply {
                layout = BorderLayout().apply {
                    hgap = PADDING
                }
                border = BorderFactory.createEmptyBorder(0, 0, PADDING, 0)
                add(JLabel("Dart class name:"), BorderLayout.WEST)
                add(classNameField, BorderLayout.CENTER)

                // Add the radio buttons
                val radioButtonPanel = JPanel().apply {
                    layout = FlowLayout() // or whatever layout you prefer

                    // Add the radio buttons to a ButtonGroup so only one can be selected at a time
                    ButtonGroup().apply {
                        add(radioButton1)
                        add(radioButton2)
                        add(radioButton3)
                    }

                    // Add the radio buttons to the panel
                    add(radioButton1)
                    add(radioButton2)
                    add(radioButton3)
                }
                add(radioButtonPanel, BorderLayout.SOUTH) // add the panel with radio buttons below the text field
            }, BorderLayout.NORTH)

        }, BorderLayout.NORTH)

        (editor as? TextEditor)?.editor?.document?.addDocumentListener(object : DocumentListener {
            override fun beforeDocumentChange(event: DocumentEvent) = Unit

            override fun documentChanged(event: DocumentEvent) {
                val json = event.document.text
                validator.validate(json) { isValid ->
                    button.isEnabled = isValid
                    formatButton.isEnabled = isValid
                }
            }
        })
    }

    fun dispose() {
        TextEditorProvider.getInstance().disposeEditor(editor)
    }
}