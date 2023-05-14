package com.lowjungxuan.proz.versionchecker.bus

import com.intellij.openapi.application.ApplicationManager
import com.intellij.util.messages.Topic

interface FlutterProjectActionSelect {


    //执行选择
    fun onSelect(appName:String)

    companion object {
        private val TOPIC = Topic.create("flutter project action select",FlutterProjectActionSelect::class.java)

        //发送
        fun fire(appName: String) {
            ApplicationManager.getApplication().messageBus.syncPublisher(TOPIC).onSelect(appName)
        }
    }


}