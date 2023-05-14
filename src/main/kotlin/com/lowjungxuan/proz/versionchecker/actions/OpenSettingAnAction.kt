package com.lowjungxuan.proz.versionchecker.actions

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.options.ShowSettingsUtil
import com.lowjungxuan.proz.versionchecker.common.MyAction
import com.lowjungxuan.proz.versionchecker.setting.AppConfig

/**
 * 打开设置
 */
class OpenSettingAnAction : MyAction(AllIcons.General.Settings) {
    override fun actionPerformed(e: AnActionEvent) {
        ShowSettingsUtil.getInstance().showSettingsDialog(e.project, AppConfig::class.java)
    }


    companion object {
        fun getInstance(): AnAction =
            ActionManager.getInstance().getAction("shop.itbug.fluttercheckversionx.actions.OpenSettingAnAction")
    }
}