package com.lowjungxuan.proz.versionchecker.util

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.MessageType
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.wm.ToolWindowManager
import com.lowjungxuan.proz.versionchecker.dialog.LoginDialogV2
import com.lowjungxuan.proz.versionchecker.icons.MyIcons
import javax.swing.JComponent

//通知相关工具类
class MyNotificationUtil {

    companion object {
        private const val toolWindowId = "Dio Request"

        //socket 相关通知
        fun socketNotify(message: String, project: Project, type: NotificationType = NotificationType.INFORMATION) {
            NotificationGroupManager.getInstance().getNotificationGroup("dio_socket_notify")
                .createNotification(message, type).apply {
                    icon = MyIcons.flutter
                }
                .notify(project)
        }


        ///弹出登录的窗口
        fun showLoginDialog(project: Project) {
//            JBPopupFactory.getInstance()
//                .createComponentPopupBuilder(loginPanel(parentDisposable), preferableFocusComponent)
//                .setMovable(true)
//                .setRequestFocus(true)
//                .setFocusable(true)
//                .createPopup().showCenteredInCurrentWindow(project)
            LoginDialogV2(project).show()
        }

    }
}