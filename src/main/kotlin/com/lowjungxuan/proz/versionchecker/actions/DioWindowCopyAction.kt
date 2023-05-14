package com.lowjungxuan.proz.versionchecker.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.lowjungxuan.proz.versionchecker.common.MyAction
import com.lowjungxuan.proz.versionchecker.document.copyTextToClipboard
import com.lowjungxuan.proz.versionchecker.i18n.PluginBundle
import com.lowjungxuan.proz.versionchecker.util.toast

/**
 * 复制链接
 */
class DioWindowCopyAction : MyAction(PluginBundle.getLazyMessage("window.idea.dio.view.copy")) {
    override fun actionPerformed(e: AnActionEvent) {
        e.api()?.url?.copyTextToClipboard()?.apply {
            e.apiListProject()?.toast("Copy succeeded!")
        }
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = e.apiListProject() != null && e.api() != null
        super.update(e)
    }


}