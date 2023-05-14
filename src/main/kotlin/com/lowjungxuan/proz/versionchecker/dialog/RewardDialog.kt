package com.lowjungxuan.proz.versionchecker.dialog

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBLabel
import java.awt.Dimension
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JPanel

/**
 * 打赏的弹窗
 */
class RewardDialog(var project: Project) : DialogWrapper(project) {

    init {
        init()
        title = "打赏"
        setOKButtonText("谢谢支持")
    }


    override fun createCenterPanel(): JComponent {
        return wxLabel

    }

    private val wxLabel: JBLabel
        get() = JBLabel().apply {
            maximumSize = Dimension(50, 50)
        }

    override fun createButtonsPanel(buttons: MutableList<out JButton>): JPanel {
        buttons.removeIf { it.actionCommand == "Cancel" }
        return super.createButtonsPanel(buttons)
    }

}