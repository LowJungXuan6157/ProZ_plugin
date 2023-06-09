package com.lowjungxuan.proz.versionchecker.window

import com.intellij.execution.ui.RunContentManagerImpl
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import com.lowjungxuan.proz.versionchecker.form.socket.SocketRequestForm
import com.lowjungxuan.proz.versionchecker.i18n.PluginBundle
import com.lowjungxuan.proz.versionchecker.icons.MyIcons
import com.lowjungxuan.proz.versionchecker.services.PluginStateService
import com.lowjungxuan.proz.versionchecker.socket.service.AppService
import com.lowjungxuan.proz.versionchecker.socket.service.DioApiService
import com.lowjungxuan.proz.versionchecker.util.toastWithError
import com.lowjungxuan.proz.versionchecker.widget.jobs.JobsWindow

//是否开启找工作窗口
const val ENABLE_FIND_JOBS_WINDOW = false
const val ENABLE_CHAT_ROOM_WINDOW = false

/**
 * dio 的请求监听窗口, 在这个窗口中,会将手机里面的一系列请求在这个窗口中显示,并可以查看详细信息
 * 梁典典: 2022年04月29日11:12:51
 */
class SocketWindow : ToolWindowFactory {

    override fun createToolWindowContent(p0: Project, p1: ToolWindow) {
        //dio 监听窗口
        val socketRequestForm = SocketRequestForm(p0, p1)
        val instance = ContentFactory.getInstance()
        val createContent =
            instance.createContent(socketRequestForm, PluginBundle.get("window.idea.dio.title"), false)

        p1.contentManager.addContent(createContent)

        val port = PluginStateService.appSetting.serverPort.toInt() // dio的监听端口


        if (AppService.getInstance().dioIsStart.not()) {
            p1.activate {
                try {
                    DioApiService.builder(port, socketRequestForm).start()
                    p1.setIcon(RunContentManagerImpl.getLiveIndicator(MyIcons.flutter))
                    AppService.getInstance().setDioSocketState(true)
                } catch (e: Exception) {
                    p0.toastWithError("Flutter dio listening service failed to start. Please try changing the port and restarting")
                }
            }
        } else {
            p1.setIcon(RunContentManagerImpl.getLiveIndicator(MyIcons.flutter))
        }

        //在线聊天窗口
        if (ENABLE_CHAT_ROOM_WINDOW) {
            val flutterChatWindow = FlutterChatMessageWindow(p0, p1)
            val flutterChatWindowContent =
                instance.createContent(flutterChatWindow, PluginBundle.get("window.idea.chat.title"), false)
            p1.contentManager.addContent(flutterChatWindowContent)
        }


        //找工作窗口
        if (ENABLE_FIND_JOBS_WINDOW) {
            val jobsWindow = JobsWindow(p0)
            val jobsContent = instance.createContent(jobsWindow, "找工作", false)
            p1.contentManager.addContent(jobsContent)
        }

        //api索引窗口
//        val apiIndexWindow = BaseApiIndexWindow(p0,p1)
//        val apiIndexContent = instance.createContent(apiIndexWindow,"接口管理",false)
//        p1.contentManager.addContent(apiIndexContent)

        //flutter收藏窗口
        val dartPluginWindow = DartPluginsWindow(p1, p0)
        val dartPluginContent = instance.createContent(dartPluginWindow, "Plugin Collects", false)
        p1.contentManager.addContent(dartPluginContent)


    }

}