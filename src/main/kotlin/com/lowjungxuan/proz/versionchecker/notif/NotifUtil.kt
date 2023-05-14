package com.lowjungxuan.proz.versionchecker.notif

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import org.jetbrains.annotations.NotNull

class NotifUtils {

    companion object {

        // 显示有新版本升级提示的通知
        fun showNewPluginTips(@NotNull project: Project, msg: String){
            NotificationGroupManager.getInstance()
                .getNotificationGroup("plugin_new_version_notify")
                .createNotification(msg,NotificationType.INFORMATION)
                .notify(project)
        }
    }
}