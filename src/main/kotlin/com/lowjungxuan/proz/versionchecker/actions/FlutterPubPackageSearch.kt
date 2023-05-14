package com.lowjungxuan.proz.versionchecker.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.lowjungxuan.proz.versionchecker.common.MyAction
import com.lowjungxuan.proz.versionchecker.dialog.SearchDialog

class FlutterPubPackageSearch : MyAction() {
    override fun actionPerformed(e: AnActionEvent) {
        e.project?.let { SearchDialog(it).show() }
    }
}