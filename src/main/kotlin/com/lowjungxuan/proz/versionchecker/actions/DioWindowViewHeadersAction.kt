package com.lowjungxuan.proz.versionchecker.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.lowjungxuan.proz.versionchecker.common.MyAction
import com.lowjungxuan.proz.versionchecker.dialog.SimpleJsonViewDialog
import com.lowjungxuan.proz.versionchecker.i18n.PluginBundle

/**
 * 查看请求头操作
 */
class DioWindowViewHeadersAction : MyAction(PluginBundle.getLazyMessage("view.request.headers")) {
    override fun actionPerformed(e: AnActionEvent) {
        e.api()?.headers?.apply { SimpleJsonViewDialog.show(this, e.apiListProject()!!) }
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = e.apiListProject()!=null && e.api()?.headers?.isNotEmpty() == true
        super.update(e)
    }
}