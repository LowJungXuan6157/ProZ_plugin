package com.lowjungxuan.proz.versionchecker.services.event

import com.intellij.openapi.application.ApplicationManager
import com.intellij.util.messages.Topic
import com.lowjungxuan.proz.versionchecker.model.user.User

interface UserLoginStatusEvent {

    /**
     * 退出登录请设置为null
     */
    fun loginSuccess(user: User?)

    companion object {
        val TOPIC = Topic.create("dd-user-login-status-change",UserLoginStatusEvent::class.java)
        fun listening(onUser: (user: User?)->Unit) {
            ApplicationManager.getApplication().messageBus.connect()
                .subscribe(UserLoginStatusEvent.TOPIC, object : UserLoginStatusEvent {
                    override fun loginSuccess(user: User?) {
                        onUser.invoke(user)
                    }
                })
        }

        fun fire(user: User?) {
            ApplicationManager.getApplication().messageBus.syncPublisher(TOPIC).loginSuccess(user)
        }

    }
}