package com.lowjungxuan.proz.versionchecker.form.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.service
import com.lowjungxuan.proz.versionchecker.socket.service.AppService

/**
 * 清空全部dio请求
 */
class DioCleanService: AnAction() {

    /// 按钮按下执行
    override fun actionPerformed(e: AnActionEvent) {
        service<AppService>().cleanAllRequest()
    }
}