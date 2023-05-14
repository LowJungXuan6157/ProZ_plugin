package com.lowjungxuan.proz.newFolder

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import java.awt.FlowLayout
import java.awt.GridLayout
import javax.swing.*


class MyDialog(project: Project) : DialogWrapper(project) {

    private val textField = JTextField(30)
    private val getxButton = JRadioButton("GetX", true)
    private val blocButton = JRadioButton("Bloc", false)
    private val buttonGroup = ButtonGroup()

    init {
        init()
        title = "ProZ Template Code Produce"
    }

    override fun createCenterPanel(): JComponent {
        val mainPanel = JPanel()
        mainPanel.layout = GridLayout(2, 1)

        buttonGroup.add(getxButton)
        buttonGroup.add(blocButton)
        getxButton.border = BorderFactory.createEmptyBorder(5, 10, 10, 100)
        blocButton.border = BorderFactory.createEmptyBorder(5, 10, 10, 100)
        /// radio button row
        val radioPanel = JPanel()
        radioPanel.border = BorderFactory.createTitledBorder("State Management")
        radioPanel.layout = FlowLayout(FlowLayout.LEFT)
        radioPanel.add(getxButton)
        radioPanel.add(blocButton)
        /// text row
        val textPanel = JPanel()
        textPanel.border = BorderFactory.createTitledBorder("Module Name")
        textPanel.layout = FlowLayout(FlowLayout.LEFT)
        textPanel.add(textField)
        /// column
        mainPanel.add(radioPanel)
        mainPanel.add(textPanel)
        return mainPanel
    }

    override fun doValidate(): ValidationInfo? {
        if (textField.text.trim().isEmpty()) {
            return ValidationInfo("Please enter some text", textField)
        }
        return super.doValidate()
    }

    // Create a method that returns an instance of the data class
    fun getDialogData(): DialogData {
        val selectedOption = when {
            getxButton.isSelected -> 1
            blocButton.isSelected -> 2
            else -> 1
        }
        return DialogData(textField.text, selectedOption)
    }
}
