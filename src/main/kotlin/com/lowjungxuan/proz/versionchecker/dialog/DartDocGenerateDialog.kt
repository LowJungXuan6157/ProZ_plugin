package com.lowjungxuan.proz.versionchecker.dialog

import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.components.BorderLayoutPanel
import org.intellij.plugins.markdown.ui.preview.MarkdownEditorWithPreview
import com.lowjungxuan.proz.versionchecker.common.MyDialogWrapper
import com.lowjungxuan.proz.versionchecker.document.copyTextToClipboard
import com.lowjungxuan.proz.versionchecker.i18n.PluginBundle
import com.lowjungxuan.proz.versionchecker.util.SwingUtil
import com.lowjungxuan.proz.versionchecker.util.Util
import javax.swing.JComponent


/**
 * dart生成文档弹窗
 */
class DartDocGenerateDialog(override val project: Project): MyDialogWrapper(project) {


    private var mkEditor : MarkdownEditorWithPreview = SwingUtil.getMkEditor(project,"")
    private var prefixTextField = JBTextField("///")

    init {
        super.init()
        title = PluginBundle.get("dart.doc.markdown")
    }

    override fun createCenterPanel(): JComponent {
        return BorderLayoutPanel().apply {
            addToCenter(mkEditor.component)
            addToTop(prefixTextField)
        }
    }

    override fun doOKAction() {
        val text = Util.addStringToLineStart(mkEditor.editor.document.text,prefixTextField.text)
        text.copyTextToClipboard()
        super.doOKAction()
    }
}