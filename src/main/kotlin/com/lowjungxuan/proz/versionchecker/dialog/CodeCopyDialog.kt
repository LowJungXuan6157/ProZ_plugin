package com.lowjungxuan.proz.versionchecker.dialog

import com.intellij.openapi.fileTypes.PlainTextLanguage
import com.intellij.openapi.project.Project
import com.intellij.ui.LanguageTextField
import com.intellij.ui.dsl.builder.Align
import com.intellij.ui.dsl.builder.panel
import com.lowjungxuan.proz.versionchecker.common.MyDialogWrapper
import com.lowjungxuan.proz.versionchecker.document.copyTextToClipboard
import javax.swing.JComponent


fun Project.showCodePreviewDialog(code: String) {
    CodeCopyDialog(this,code).show()
}

class CodeCopyDialog(override val project: Project, val code: String) : MyDialogWrapper(project) {

    val editor = LanguageTextField(
        PlainTextLanguage.INSTANCE,
        project,
        code,
        false

    )

    init {
        super.init()
        title = "Copy code dialog"
        super.setOKButtonText("Copy Code")
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            row {
                scrollCell(editor).align(Align.FILL)
            }
        }
    }


    override fun doOKAction() {
        editor.text.copyTextToClipboard()
        super.doOKAction()
    }
}