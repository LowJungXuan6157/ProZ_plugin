package com.lowjungxuan.proz.versionchecker.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.lowjungxuan.proz.versionchecker.common.MyAction
import com.lowjungxuan.proz.versionchecker.config.GenerateAssetsClassConfig
import com.lowjungxuan.proz.versionchecker.dialog.AssetsAutoGenerateClassActionConfigDialog
import com.lowjungxuan.proz.versionchecker.i18n.PluginBundle
import com.lowjungxuan.proz.versionchecker.util.MyDartPsiElementUtil


/// 自动正常资产文件调用
///
class AssetsAutoGenerateClassAction : MyAction(PluginBundle.getLazyMessage("assets.gen")) {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.getData(CommonDataKeys.PROJECT)!!
        val vf = e.getData(CommonDataKeys.VIRTUAL_FILE)!!
        val name = vf.name
        val userSetting = GenerateAssetsClassConfig.getGenerateAssetsSetting()
        val isOk =
            if (userSetting.dontTip) true else AssetsAutoGenerateClassActionConfigDialog(project).showAndGet()
        if (!isOk) {
            return
        }
        MyDartPsiElementUtil.autoGenerateAssetsDartClassFile(project, name, false, userSetting)
    }

    override fun update(e: AnActionEvent) {
        val vf = e.getData(CommonDataKeys.VIRTUAL_FILE)
        e.presentation.isEnabledAndVisible = vf != null && vf.isDirectory && e.project != null
        super.update(e)
    }

}