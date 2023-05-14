package com.lowjungxuan.proz.versionchecker.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.lowjungxuan.proz.versionchecker.common.MyAction
import com.lowjungxuan.proz.versionchecker.dialog.SimpleJsonViewDialog
import com.lowjungxuan.proz.versionchecker.i18n.PluginBundle

/**
 * 查看post参数
 */
class DioWindowViewPostParamsAction : MyAction(PluginBundle.getLazyMessage("dio.toolbar.post.params")) {
    override fun actionPerformed(e: AnActionEvent) {
        val api = e.api()!!
        api.body?.let { SimpleJsonViewDialog.show(it, e.apiListProject()!!) }
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
        e.presentation.isEnabled = e.api()?.body is Map<*, *> && e.apiListProject() != null
    }

}