package com.lowjungxuan.proz.versionchecker.dialog

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.panel
import org.smartboot.socket.StateMachineEnum
import org.smartboot.socket.StateMachineEnum.*
import org.smartboot.socket.transport.AioSession
import com.lowjungxuan.proz.versionchecker.bus.SocketConnectStatusMessageBus
import com.lowjungxuan.proz.versionchecker.socket.service.AppService
import javax.swing.JComponent


class DioListenServerStateDialog(val project: Project) : DialogWrapper(project) {


    private var service: AppService = service<AppService>()
    private var status: StateMachineEnum? = service.dioServerStatus

    private var threadIsAlive = false



    init {
        super.init()
        title = "Dio Listen to server status"
        changeListen()
        checkDioThread()
    }


    private fun checkDioThread() {
        threadIsAlive = service.dioThread.isAlive
        repaint()
    }

    /**
     * 监听aio状态变化
     */
    private fun changeListen() {
        ApplicationManager.getApplication().messageBus.connect()
            .subscribe(SocketConnectStatusMessageBus.CHANGE_ACTION_TOPIC, object : SocketConnectStatusMessageBus {
                override fun statusChange(aioSession: AioSession?, stateMachineEnum: StateMachineEnum?) {
                    status = stateMachineEnum
                    repaint()
                }
            })
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            row("dio线程状态") {
                button("检测") {
                    checkDioThread()
                }
                label(if(threadIsAlive) "活跃状态" else "已关闭")
            }

        }
    }

}