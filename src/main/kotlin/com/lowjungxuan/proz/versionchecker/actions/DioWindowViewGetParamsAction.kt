package com.lowjungxuan.proz.versionchecker.actions

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.lowjungxuan.proz.versionchecker.common.MyDumbAwareAction
import com.lowjungxuan.proz.versionchecker.dialog.SimpleJsonViewDialog
import com.lowjungxuan.proz.versionchecker.i18n.PluginBundle

/**
 * 查看get参数
 */
open class DioWindowViewGetParamsAction : MyDumbAwareAction(PluginBundle.getLazyMessage("dio.toolbar.get.params")) {

    override fun actionPerformed(e: AnActionEvent) {
        val api = e.api()!!
         api.queryParams?.let { SimpleJsonViewDialog.show(it, e.apiListProject()!!) }

    }

    override fun update(e: AnActionEvent) {
        super.update(e)
        e.presentation.isEnabled = e.api()?.queryParams?.isNotEmpty() == true && e.apiListProject() !=null
    }



    companion object {
        val instance: AnAction = ActionManager.getInstance().getAction("dio-window-view-GET")
    }

}