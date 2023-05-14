package com.lowjungxuan.proz.versionchecker.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.service
import com.intellij.openapi.project.DumbAware
import com.lowjungxuan.proz.versionchecker.common.MyToggleAction
import com.lowjungxuan.proz.versionchecker.i18n.PluginBundle
import com.lowjungxuan.proz.versionchecker.socket.service.AppService

/**
 * 自动滚动到最底部
 */
class DioWindowAutoScrollToMaxAction : MyToggleAction(PluginBundle.getLazyMessage("auto.scroll.to.the.bottom")),
    DumbAware {

    private val appService = service<AppService>()
    override fun isSelected(e: AnActionEvent): Boolean {
        return appService.apiListAutoScrollerToMax
    }

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        appService.setIsAutoScrollToMax(state)
    }


}