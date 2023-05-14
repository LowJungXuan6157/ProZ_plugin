package com.lowjungxuan.proz.versionchecker.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.lowjungxuan.proz.versionchecker.common.MyAction
import com.lowjungxuan.proz.versionchecker.dialog.DartDocGenerateDialog
import com.lowjungxuan.proz.versionchecker.i18n.PluginBundle

class DartDocumentGenerateAction: MyAction(PluginBundle.getLazyMessage("dart.doc.markdown")) {
    override fun actionPerformed(e: AnActionEvent) {
        DartDocGenerateDialog(e.project!!).show()
    }


    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = e.project != null
        super.update(e)
    }
}